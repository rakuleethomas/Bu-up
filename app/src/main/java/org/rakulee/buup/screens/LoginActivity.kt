package org.rakulee.buup.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.parse.Parse
import com.parse.ParseObject
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityLoginBinding


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.activity = this

        Parse.initialize(
            Parse.Configuration.Builder(this)
            .applicationId("aVQUhSqemZDmI8n0mWYOSPzWBAlPb4YPUCIbtH6Q") // should correspond to Application ID env variable
            .clientKey("hrcIMfNQZESdJVFY5H3g11rkvQK9d5jRWruyQjfA")  // should correspond to Client key env variable
            .server("https://parseapi.back4app.com").build());


    }

    fun doLogin(){
        if(binding.tabLayout.getTabAt(0)!!.isSelected){
            val intent = Intent(this, PartTimeEmployerActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Login as employer", Toast.LENGTH_SHORT).show()
        }else if(binding.tabLayout.getTabAt(1)!!.isSelected){
            val intent = Intent(this, PartTimeJobSeekerActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Login as job seeker", Toast.LENGTH_SHORT).show()
        }
    }

    fun goSignup(){
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
}