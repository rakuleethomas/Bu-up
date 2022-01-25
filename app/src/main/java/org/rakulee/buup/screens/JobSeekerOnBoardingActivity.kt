package org.rakulee.buup.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.BaseActivity
import org.rakulee.buup.Configs
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityJobSeekerOnBoardingBinding
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.viewmodel.JobSeekerOnBoardingViewModel
import org.rakulee.buup.viewmodel.JobSeekerViewModel


/**
 *  JobSeeker OnBoarding Activity
 *  (Navigation graph initiation point)
 *  Need to declare viewmodels for JobSeeker OnBoarding Graph
 */

@AndroidEntryPoint
class JobSeekerOnBoardingActivity : BaseActivity() {


    lateinit var binding: ActivityJobSeekerOnBoardingBinding
    val jobSeekerViewModel : JobSeekerOnBoardingViewModel by viewModels()     // To access from fragments, initialize  by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configs.currentThemeMode = Configs.BUUP_ONBOARDING
        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_seeker_on_boarding)
        binding.lifecycleOwner = this

//        val gson = Gson()
//        val buupJobSeekerProfile = gson.fromJson<BuupJobSeekerProfile>(intent.getStringExtra("JobSeekerProfileJson"), BuupJobSeekerProfile::class.java)
//        jobSeekerViewModel.updateBuupJobSeekerProfile(buupJobSeekerProfile)

    }
}