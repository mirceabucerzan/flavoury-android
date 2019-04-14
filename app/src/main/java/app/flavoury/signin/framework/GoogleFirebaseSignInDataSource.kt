package app.flavoury.signin.framework

import android.content.Context
import android.content.Intent
import app.flavoury.BuildConfig
import app.flavoury.signin.datasource.GoogleSignInDataSource
import app.flavoury.signin.domain.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume

/**
 * Data source for Google Firebase sign in.
 */
class GoogleFirebaseSignInDataSource(private val context: Context) : GoogleSignInDataSource<Intent> {

    override fun getSignInInitData(): Intent {
        val signInOptions: GoogleSignInOptions.Builder =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).apply {
                requestEmail()
                requestIdToken(BuildConfig.GOOGLE_OAUTH_2_WEB_CLIENT_ID)
            }
        return GoogleSignIn.getClient(context, signInOptions.build()).signInIntent
    }

    override suspend fun signIn(data: Intent): User? {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            account ?: return null
            val credential: AuthCredential? = GoogleAuthProvider.getCredential(account.idToken, null)
            credential ?: return null

            // convert firebase callback to coroutine
            return suspendCancellableCoroutine { continuation ->
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { authResult ->
                    var user: User? = null
                    if (authResult.isSuccessful) user = FirebaseAuth.getInstance().currentUser?.toDomainUser()
                    if (user == null) {
                        Timber.e("Google/Firebase sign in failed")
                    } else {
                        Timber.d("Google/Firebase sign in successful: $user")
                    }
                    continuation.resume(user)
                }
            }

        } catch (e: ApiException) {
            Timber.e(e, "Google/Firebase sign in failed")
            return null
        }
    }
}