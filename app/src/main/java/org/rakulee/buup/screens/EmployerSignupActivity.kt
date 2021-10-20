package org.rakulee.buup.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.BaseActivity
import org.rakulee.buup.Configs
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityEmployerSignupBinding
import org.rakulee.buup.model.BuupEmployerProfile
import org.rakulee.buup.util.Util
import java.io.Serializable

@AndroidEntryPoint
class EmployerSignupActivity : BaseActivity(), Serializable {
    lateinit var binding : ActivityEmployerSignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configs.currentThemeMode = Configs.BUUP_ONBOARDING
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employer_signup)
        binding.lifecycleOwner = this
    }

    fun goBack(view : View){
        super.onBackPressed()
    }



    fun startOnBoarding(view : View){
        if(validate()){
            val buupEmployerProfile = BuupEmployerProfile()
            val plainPassword = binding.etPassword.text.toString()
            val encryptedPassword = Util.encryptPassword(plainPassword)
            buupEmployerProfile.email = binding.etEmail.text.toString()
            buupEmployerProfile.loginId = binding.etEmail.text.toString()
            buupEmployerProfile.password = encryptedPassword.toString()
            // serialize buupEmployerProfile
            val gson = Gson()
            val temp = gson.toJson(buupEmployerProfile)
            val intent = Intent(this, EmployerOnBoardingActivity::class.java)
            intent.putExtra("EmployerProfileJson", temp)
            startActivity(intent)
            finish()
        }else{
            when{
                binding.etPassword.text.equals("") -> Toast.makeText(applicationContext, "Password should not be empty", Toast.LENGTH_SHORT)
                    .show()
                binding.etEmail.text.equals("") -> Toast.makeText(applicationContext, "Email should not be empty", Toast.LENGTH_SHORT).show()
                binding.etRePassword.text.equals("") -> Toast.makeText(applicationContext, "Retype Password should not be empty", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
    fun validate() : Boolean{
        val password = binding.etPassword.text.toString()
        val rePassword = binding.etRePassword.text.toString()
        if(!password.equals(rePassword)){
            Toast.makeText(applicationContext, "Password & re-type password are not identical", Toast.LENGTH_SHORT).show()
        }


        return binding.etPassword.text.toString().equals(binding.etRePassword.text.toString())
    }
}