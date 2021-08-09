package org.rakulee.buup.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.parse.Parse
import com.parse.ParseUser
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivitySignupBinding

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        Parse.initialize(
//            Parse.Configuration.Builder(this)
//                .applicationId("aVQUhSqemZDmI8n0mWYOSPzWBAlPb4YPUCIbtH6Q") // should correspond to Application ID env variable
//                .clientKey("hrcIMfNQZESdJVFY5H3g11rkvQK9d5jRWruyQjfA")  // should correspond to Client key env variable
//                .server("https://parseapi.back4app.com").build());

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding.lifecycleOwner = this
        binding.activity = this

    }


//    fun doSignup() {
//
//
//        val user = ParseUser()
//        val intent = Intent(this, LoginActivity::class.java)
//
//        with(user) {
//            username = binding.etUsername.text.toString()
//            setPassword(binding.etPassword.text.toString())
//            email = binding.etEmail.text.toString()
//            signUpInBackground { e ->
//                if (e == null) {
//                } else {
//                    Log.e("SignUpActivity - ", e.toString());
//                }
//            }
//
//        }
//        startActivity(intent)
//        Toast.makeText(this, binding.etUsername.text.toString() + " was successfully signed up!" , Toast.LENGTH_SHORT).show()
//    }
}