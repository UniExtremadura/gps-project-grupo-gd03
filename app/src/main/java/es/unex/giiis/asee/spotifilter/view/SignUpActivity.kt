package es.unex.giiis.asee.spotifilter.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.giiis.asee.spotifilter.data.model.User
import es.unex.giiis.asee.spotifilter.database.SpotiFilterDatabase
import es.unex.giiis.asee.spotifilter.databinding.ActivitySignUpBinding
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var database: SpotiFilterDatabase

    companion object {

        const val USER = "SIGN_UP_USER"

        fun start(context: Context, responseLauncher: ActivityResultLauncher<Intent>) {
            val intent = Intent(context, SignUpActivity::class.java)
            responseLauncher.launch(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = SpotiFilterDatabase.getInstance(this)!!
        setUpListeners()
    }

    private fun navigateBackWithResult(user: User) {
        val intent = Intent().apply {
            putExtra(USER, user)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun setUpListeners() {
        binding.signUpButton.setOnClickListener {
            lifecycleScope.launch {
                signUp()
            }
        }
    }

    private suspend fun signUp() {
        val name = binding.signUpPlainText.text.toString()
        val password1 = binding.signUpPassword1.text.toString()
        val password2 = binding.signUpPassword2.text.toString()
        if (name.isBlank() || name.length < 4) {
            Toast.makeText(this@SignUpActivity, "Invalid name",
                Toast.LENGTH_SHORT).show()
        } else if (database.userDao().findByName(name) != null) {
            Toast.makeText(this@SignUpActivity, "Unavailable name",
                Toast.LENGTH_SHORT).show()
        } else if (password1.isBlank() || password1.length < 4) {
            Toast.makeText(this@SignUpActivity, "Invalid password",
                Toast.LENGTH_SHORT).show()
        } else if (password1 != password2) {
            Toast.makeText(this@SignUpActivity, "Passwords don't match",
                Toast.LENGTH_SHORT).show()
        } else {
            val user = User(null, name, password1)
            user.id = database.userDao().insert(user)
            navigateBackWithResult(user)
        }
    }

}