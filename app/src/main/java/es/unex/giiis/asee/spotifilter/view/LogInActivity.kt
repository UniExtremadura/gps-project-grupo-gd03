package es.unex.giiis.asee.spotifilter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.unex.giiis.asee.spotifilter.databinding.ActivityLogInBinding
import es.unex.giiis.asee.spotifilter.view.home.HomeActivity

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        setUpListeners()
    }

    private fun navigateToHomeActivity() {
        HomeActivity.start(this)
    }

    private fun navigateToSignUpActivity() {
        SignUpActivity.start(this)
    }

    private fun setUpListeners() {
        with(binding) {
            logInButton1.setOnClickListener {
                navigateToHomeActivity()
            }
            logInButton2.setOnClickListener {
                navigateToSignUpActivity()
            }
        }
    }

    private fun setUpUI() {

    }

}