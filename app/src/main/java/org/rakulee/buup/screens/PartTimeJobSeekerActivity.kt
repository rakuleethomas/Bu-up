package org.rakulee.buup.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityPartTimeJobSeekerBinding
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.viewmodel.JobSeekerViewModel

@AndroidEntryPoint
class PartTimeJobSeekerActivity : AppCompatActivity() {
    lateinit var binding : ActivityPartTimeJobSeekerBinding
    private val viewModel : JobSeekerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_part_time_job_seeker)
        binding.lifecycleOwner = this

        CoroutineScope(Dispatchers.Main).launch {
            val gson = Gson()
            val buupJobseekerProfile = gson.fromJson<BuupJobSeekerProfile>(intent.getStringExtra("JobSeekerProfileJson"), BuupJobSeekerProfile::class.java)
            viewModel.updateBuupJobSeekerProfile(buupJobseekerProfile)
        }

        NavigationUI.setupWithNavController(binding.bottomNavigationJobSeeker, findNavController(R.id.nav_host_job_seeker))
    }
}