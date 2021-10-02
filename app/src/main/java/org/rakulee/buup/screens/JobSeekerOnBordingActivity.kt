package org.rakulee.buup.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.adapters.ViewPageAdapter
import org.rakulee.buup.databinding.ActivityJobSeekerOnBordingBinding


@AndroidEntryPoint
class JobSeekerOnBordingActivity : AppCompatActivity() {


    lateinit var binding: ActivityJobSeekerOnBordingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_seeker_on_bording)
        binding.lifecycleOwner = this


    }
}