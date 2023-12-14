package es.unex.giiis.asee.spotifilter.view

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import es.unex.giiis.asee.spotifilter.data.model.User
import es.unex.giiis.asee.spotifilter.database.SpotiFilterDatabase
import es.unex.giiis.asee.spotifilter.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var database: SpotiFilterDatabase
    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                with(result.data) {
                    val user = this?.getSerializableExtra(SignUpActivity.USER) as User
                    binding.logInPlainText.setText(user.name)
                    binding.logInPassword.setText(user.password)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = SpotiFilterDatabase.getInstance(this)!!
        setUpListeners()
        readSettings()
    }

    private fun readSettings() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this).all
        val rememberMe = preferences["rememberMe"] as Boolean? ?: false
        val name = preferences["name"] as String? ?: ""
        val password = preferences["password"] as String? ?: ""
        if (rememberMe) {
            binding.logInPlainText.setText(name)
            binding.logInPassword.setText(password)
        }
    }

    private fun setUpListeners() {
        binding.logInButton2.setOnClickListener {
            SignUpActivity.start(this@LogInActivity, responseLauncher)
        }
    }

}