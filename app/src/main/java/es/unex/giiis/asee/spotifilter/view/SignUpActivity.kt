package es.unex.giiis.asee.spotifilter.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.unex.giiis.asee.spotifilter.databinding.ActivitySignUpBinding
import es.unex.giiis.asee.spotifilter.view.home.HomeActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        setUpListeners()
    }

    private fun navigateToHomeActivity() {
        HomeActivity.start(this)
    }

    private fun setUpListeners() {
        with(binding) {
            signUpButton.setOnClickListener {
                navigateToHomeActivity()
            }
        }
    }

    private fun setUpUI() {

    }

}