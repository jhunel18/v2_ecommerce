package ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ph.stacktrek.novare.ecommercenovare.penaflorida.jhunel.utility.PreferenceUtility
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.database.AppDatabase
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.databinding.ActivityLoginBinding
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.databinding.ActivityRegisterBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var registerUsernameData:String
    private lateinit var registerPasswordData:String
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var db: AppDatabase

    private val launchRegister = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        registerUsernameData= data!!.getStringExtra("register_username").toString()
        registerPasswordData = data.getStringExtra("register_password").toString()


        loginBinding.usernametext.setText(registerUsernameData)
        loginBinding.passwordtext.setText(registerPasswordData)

        Snackbar.make(loginBinding.root,
            "Registered ${data.getStringExtra("register_username")}",
            Snackbar.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "my_db"
        ).build()
        loginBinding.registerButton.setOnClickListener {
            val goToRegister = Intent(
                applicationContext,
                RegisterActivity::class.java
            )
            launchRegister.launch(goToRegister)
        }

        loginBinding.loginButton.setOnClickListener {
            var username = loginBinding.usernametext.text.toString()
            var password = loginBinding.passwordtext.text.toString()

            // Use coroutines to execute the database query on a background thread
            CoroutineScope(Dispatchers.IO).launch {
                val userDao = db.userDao()
                val user = userDao.findByUsernameAndPassword(username, password)

                // Switch back to the main thread to update the UI
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        val goToMain = Intent(applicationContext, MainActivity::class.java)
                        goToMain.putExtra("username", username)
                        val bundle = Bundle()
                        bundle.putString("bundle_username", username)
                        goToMain.putExtras(bundle)

                        //Preference Utility to store username and password

                        PreferenceUtility(applicationContext).apply {
                            saveStringPreferences("username", loginBinding.usernametext.text.toString())
                            saveStringPreferences("password", loginBinding.passwordtext.text.toString())
                        }

                        startActivity(goToMain)
                        finish()
                    } else {
                        Snackbar.make(
                            loginBinding.root,
                            "Invalid Credentials",
                            Snackbar.LENGTH_SHORT).show()
                    }
                }
           }
        }
        //Preference Utility
        PreferenceUtility(applicationContext).apply {
            loginBinding.usernametext.setText(getStringPreferences("username" ))
            loginBinding.passwordtext.setText(getStringPreferences("password" ))
        }
    }
}