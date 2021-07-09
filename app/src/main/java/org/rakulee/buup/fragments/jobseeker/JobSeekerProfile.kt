package org.rakulee.buup.fragments.jobseeker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.adapters.JobSeekerProfileExperiencesListAdapter
import org.rakulee.buup.adapters.JobSeekerProfileInterestListAdapter
import org.rakulee.buup.adapters.JobSeekerProfileSkillsListAdapter
import org.rakulee.buup.databinding.FragmentJobSeekerProfileBinding
import org.rakulee.buup.model.JobSeekerExperiences
import org.rakulee.buup.model.JobSeekerInterestItem
import org.rakulee.buup.model.JobSeekerSkill

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobSeekerProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class JobSeekerProfile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding : FragmentJobSeekerProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_profile, container, false)
        binding.lifecycleOwner = this
        binding.viewOnly = false

        binding.tvName.text = "Thomas"
        binding.tvBio.text = "Android App Developer"
        binding.tvWageValue.text = "$25 - $30 /hr"

        var interestList = ArrayList<JobSeekerInterestItem>()
        var skillsList = ArrayList<JobSeekerSkill>()
        var expList = ArrayList<JobSeekerExperiences>()
        interestList.add(JobSeekerInterestItem("Restaurant"))
        interestList.add(JobSeekerInterestItem("helllo"))
        interestList.add(JobSeekerInterestItem("test1"))
        interestList.add(JobSeekerInterestItem("test2"))
        interestList.add(JobSeekerInterestItem("Customer Service"))
        interestList.add(JobSeekerInterestItem("Sales"))
        interestList.add(JobSeekerInterestItem("Marketing"))
        interestList.add(JobSeekerInterestItem("Translation"))


        skillsList.add(JobSeekerSkill("POS"))
        skillsList.add(JobSeekerSkill("Computer Science"))
        skillsList.add(JobSeekerSkill("Cooking"))
        skillsList.add(JobSeekerSkill("Customer Service"))
        skillsList.add(JobSeekerSkill("Education Consulting"))
        skillsList.add(JobSeekerSkill("Counseling"))

        expList.add(JobSeekerExperiences("Bonchon Chicken", "Cashier", "Mar 2021", "May 2021"))
        expList.add(JobSeekerExperiences("Osteria Toscana", "Server", "Feb 2020", "Feb 2021"))
        expList.add(JobSeekerExperiences("De Anza College", "Cook", "Jan 2019", "Jan 2020"))

        val interestListAdapter = JobSeekerProfileInterestListAdapter()
        interestListAdapter.updateItems(interestList)

        val skillsListAdapter = JobSeekerProfileSkillsListAdapter()
        skillsListAdapter.updateItems(skillsList)

        val experiencesListAdapter = JobSeekerProfileExperiencesListAdapter()
        experiencesListAdapter.updateItems(expList)

        binding.rvInterest.adapter = interestListAdapter
        binding.rvSkills.adapter = skillsListAdapter
        binding.rvExpList.adapter = experiencesListAdapter


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JobSeekerTest3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JobSeekerProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}