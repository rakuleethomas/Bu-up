package org.rakulee.buup.fragments.jobseeker.profileEdit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import org.rakulee.buup.R
import org.rakulee.buup.adapters.JobSeekerProfileSkillEditListAdapter
import org.rakulee.buup.adapters.JobSeekerProfileSkillSelectedListAdapter
import org.rakulee.buup.adapters.JobSeekerProfileSkillsListAdapter
import org.rakulee.buup.databinding.FragmentProfileSkillEditBinding
import org.rakulee.buup.model.JobSeekerSkill

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileSkillEdit.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileSkillEdit : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentProfileSkillEditBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_skill_edit, container, false)
        binding.lifecycleOwner = this
        binding.editSkill = this

        var skillsList = ArrayList<JobSeekerSkill>()
        var skillsSelectedList = ArrayList<JobSeekerSkill>()


        skillsList.add(JobSeekerSkill("POS"))
        skillsList.add(JobSeekerSkill("Computer Science"))
        skillsList.add(JobSeekerSkill("Cooking"))
        skillsList.add(JobSeekerSkill("Customer Service"))
        skillsList.add(JobSeekerSkill("Education Consulting"))
        skillsList.add(JobSeekerSkill("Counseling"))
        skillsList.add(JobSeekerSkill("POS"))
        skillsList.add(JobSeekerSkill("Computer Science"))
        skillsList.add(JobSeekerSkill("Cooking"))
        skillsList.add(JobSeekerSkill("Customer Service"))
        skillsList.add(JobSeekerSkill("Education Consulting"))
        skillsList.add(JobSeekerSkill("Counseling"))


        skillsSelectedList.add(JobSeekerSkill("POS"))
        skillsSelectedList.add(JobSeekerSkill("Computer Science"))
        skillsSelectedList.add(JobSeekerSkill("Cooking"))
        skillsSelectedList.add(JobSeekerSkill("Customer Service"))
        skillsSelectedList.add(JobSeekerSkill("Education Consulting"))
        skillsSelectedList.add(JobSeekerSkill("Counseling"))


        val skillsListAdapter = JobSeekerProfileSkillEditListAdapter()
        skillsListAdapter.updateItems(skillsList)

        val skillsSelectedAdapter = JobSeekerProfileSkillSelectedListAdapter()
        skillsSelectedAdapter.updateItems(skillsSelectedList)

        binding.rvSkills.adapter = skillsListAdapter
        binding.recyclerViewSelectedSkill.adapter = skillsSelectedAdapter

        binding.cancel.setOnClickListener{
            val direction : NavDirections = ProfileSkillEditDirections.actionEditSkillToProfile()
            findNavController().navigate(direction)
        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileSkillEdit.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileSkillEdit().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}