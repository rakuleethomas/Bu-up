package org.rakulee.buup.fragments.jobseeker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentJobSeekerOnBoarding1Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class JobSeekerOnBoarding1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    lateinit var binding : FragmentJobSeekerOnBoarding1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_on_boarding1, container, false)
        binding.lifecycleOwner = this
        binding.onBoarding1 = this


        return binding.root
    }

}