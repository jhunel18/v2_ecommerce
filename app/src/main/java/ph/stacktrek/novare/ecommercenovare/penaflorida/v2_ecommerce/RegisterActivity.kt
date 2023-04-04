package ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.database.AppDatabase
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.databinding.ActivityRegisterBinding
import ph.stacktrek.novare.ecommercenovare.penaflorida.v2_ecommerce.entity.UserEntity

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "my_db").build()

        registerBinding.btnRegister.setOnClickListener{
            val activity = this@RegisterActivity
            val username = registerBinding.registerUsernameText.text.toString()
            val password = registerBinding.registerPasswordText.text.toString()
            val confirmPassword = registerBinding.registerConfirmPasswordText.text.toString()

            CoroutineScope(Dispatchers.IO).launch{
                if (username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            activity,
                            "Values cannot be empty!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else{
                    val existingUsername = db.userDao().getUserByUsername(username)

                    if(existingUsername !=null){
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                activity,
                                "Username Exists!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else{
                        val user =UserEntity(username = username,
                            password = password,
                            confirmPassword = confirmPassword
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            db.userDao().insert(user)

                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    activity,
                                    "User Registered Successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        val intent = Intent()
                        intent.putExtra("register_username", registerBinding.registerUsernameText.text.toString())
                        intent.putExtra("register_password", registerBinding.registerPasswordText.text.toString())
                        setResult(1, intent)
                        finish()
                    }
                }
            }

        }
    }
    override fun onBackPressed() {
        val goToLogin = Intent(applicationContext, LoginActivity::class.java)
        startActivity(goToLogin)
        finish()
    }

    }
