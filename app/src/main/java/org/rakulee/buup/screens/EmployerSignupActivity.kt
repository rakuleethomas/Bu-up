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
import org.rakulee.buup.databinding.ActivityEmployerSignupBinding

@AndroidEntryPoint
class EmployerSignupActivity : BaseActivity() {
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
        val intent = Intent(this, EmployerOnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}