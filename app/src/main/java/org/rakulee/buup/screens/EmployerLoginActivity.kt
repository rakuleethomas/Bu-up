package org.rakulee.buup.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.BaseActivity
import org.rakulee.buup.Configs
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityEmployerLoginBinding

@AndroidEntryPoint
class EmployerLoginActivity : BaseActivity() {
    lateinit var binding : ActivityEmployerLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configs.currentThemeMode = Configs.BUUP_ONBOARDING
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employer_login)
        binding.lifecycleOwner = this


    }


    fun createAccount(view : View){
        val intent = Intent(this, EmployerSignupActivity::class.java)
        startActivity(intent)
    }
}