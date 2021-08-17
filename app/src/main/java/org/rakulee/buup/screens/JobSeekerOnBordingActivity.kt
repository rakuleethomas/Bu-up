package org.rakulee.buup.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
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

        binding.viewPager.adapter = ViewPageAdapter(this)
        binding.wormDotsIndicator.setViewPager2(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position){
                    0 -> {binding.tvWage.setTextColor(resources.getColor(R.color.text1))
                        binding.tvIndustry.setTextColor(resources.getColor(R.color.text2))
                        binding.tvLocation.setTextColor(resources.getColor(R.color.text2))}
                    1 -> {binding.tvWage.setTextColor(resources.getColor(R.color.text2))
                        binding.tvIndustry.setTextColor(resources.getColor(R.color.text1))
                        binding.tvLocation.setTextColor(resources.getColor(R.color.text2))}
                    2 -> {binding.tvWage.setTextColor(resources.getColor(R.color.text2))
                        binding.tvIndustry.setTextColor(resources.getColor(R.color.text2))
                        binding.tvLocation.setTextColor(resources.getColor(R.color.text1))}

                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

    }
}