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
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityPartTimeEmployerBinding
import org.rakulee.buup.fragments.employer.EmployerProfile
import org.rakulee.buup.fragments.employer.employer_post.EmployerJobPosting
import org.rakulee.buup.model.BuupEmployerProfile
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.viewmodel.EmployerViewModel

@AndroidEntryPoint
class PartTimeEmployerActivity : BaseActivity() {
    lateinit var binding : ActivityPartTimeEmployerBinding
    private val viewModel : EmployerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra("EmployerProfileJson")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_part_time_employer)
        binding.lifecycleOwner = this

        val gson = Gson()
        val buupEmployerProfile = gson.fromJson<BuupEmployerProfile>(intent.getStringExtra("EmployerProfileJson"), BuupEmployerProfile::class.java)
        viewModel.updateEmployerInfo(buupEmployerProfile)

        NavigationUI.setupWithNavController(binding.bottomNavigationEmployer, findNavController(R.id.nav_host_employer))
    }

    interface EmployerJobPostingHelper{
        fun updateStatus(editBtn : org.rakulee.buup.fragments.employer.employer_post.EmployerJobPosting.Edit)
    }
}