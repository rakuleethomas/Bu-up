package org.rakulee.buup.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.BaseActivity
import org.rakulee.buup.Configs
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityEmployerOnBoardingBinding
import org.rakulee.buup.model.BuupEmployerProfile
import org.rakulee.buup.viewmodel.EmployerOnBoardingViewModel

@AndroidEntryPoint
class EmployerOnBoardingActivity : BaseActivity() {
    lateinit var binding : ActivityEmployerOnBoardingBinding
    private val viewModel : EmployerOnBoardingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configs.currentThemeMode = Configs.BUUP_ONBOARDING
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employer_on_boarding)
        binding.lifecycleOwner = this

        /**
         * The host fragment is EmployerOnBoarding1
         */

        val gson = Gson()
        val buupEmployerProfile = gson.fromJson<BuupEmployerProfile>(intent.getStringExtra("EmployerProfileJson"), BuupEmployerProfile::class.java)
        viewModel.updateBuupEmployerProfile(buupEmployerProfile)
    }
}