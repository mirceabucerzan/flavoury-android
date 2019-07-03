package app.flavoury.signin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * The sign in feature's single entry point.
 */
internal class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

}