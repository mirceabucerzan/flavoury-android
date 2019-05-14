package app.flavoury

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.flavoury.signin.SignInActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO Update to check if already signed in
        startActivity(Intent(this, SignInActivity::class.java))
    }

}