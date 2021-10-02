package org.rakulee.buup.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.rakulee.buup.fragments.jobseeker.JobSeekerOnBoarding1
import org.rakulee.buup.fragments.jobseeker.JobSeekerOnBoarding2
import org.rakulee.buup.fragments.jobseeker.JobSeekerOnBoarding3

class ViewPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return JobSeekerOnBoarding1()
            1 -> return JobSeekerOnBoarding2()
            2 -> return JobSeekerOnBoarding3()
        }
        return JobSeekerOnBoarding1()
    }

    override fun getItemCount(): Int {
        return 3;
    }
}