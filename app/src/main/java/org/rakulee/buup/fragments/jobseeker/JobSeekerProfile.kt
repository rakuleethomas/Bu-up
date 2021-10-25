package org.rakulee.buup.fragmets.jobseeker

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.parse.ParseUser
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.adapters.JobSeekerProfileExperiencesListAdapter
import org.rakulee.buup.adapters.JobSeekerProfileInterestListAdapter
import org.rakulee.buup.adapters.JobSeekerProfileSkillsListAdapter
import org.rakulee.buup.databinding.FragmentJobSeekerProfileBinding
import org.rakulee.buup.fragments.jobseeker.profileEdit.ProfileUserEdit
import org.rakulee.buup.model.JobSeekerExperiences
import org.rakulee.buup.model.JobSeekerInterestItem
import org.rakulee.buup.model.JobSeekerSkill
import org.rakulee.buup.viewmodel.JobSeekerViewModel

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
    private val viewModel: JobSeekerViewModel by activityViewModels()
    private lateinit var morningImageViewList : ArrayList<ImageView>
    private lateinit var afternoonImageViewList : ArrayList<ImageView>
    private lateinit var nightImageViewList : ArrayList<ImageView>

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
        binding.viewModel = viewModel

        morningImageViewList = ArrayList()
        afternoonImageViewList = ArrayList()
        nightImageViewList = ArrayList()

        morningImageViewList.add(binding.imageView4)
        morningImageViewList.add(binding.imageView5)
        morningImageViewList.add(binding.imageView6)
        morningImageViewList.add(binding.imageView7)
        morningImageViewList.add(binding.imageView8)
        morningImageViewList.add(binding.imageView9)
        morningImageViewList.add(binding.imageView10)



        afternoonImageViewList.add(binding.iv1)
        afternoonImageViewList.add(binding.iv2)
        afternoonImageViewList.add(binding.iv3)
        afternoonImageViewList.add(binding.iv4)
        afternoonImageViewList.add(binding.iv5)
        afternoonImageViewList.add(binding.iv6)
        afternoonImageViewList.add(binding.iv7)

        nightImageViewList.add(binding.ivNight1)
        nightImageViewList.add(binding.ivNight2)
        nightImageViewList.add(binding.ivNight3)
        nightImageViewList.add(binding.ivNight4)
        nightImageViewList.add(binding.ivNight5)
        nightImageViewList.add(binding.ivNight6)
        nightImageViewList.add(binding.ivNight7)

        binding.btnDropdown.setOnClickListener{
            if(binding.constraintLayout9.visibility == View.VISIBLE){
                binding.constraintLayout9.visibility = View.GONE
            } else{
                binding.constraintLayout9.visibility = View.VISIBLE
            }
        }
        if (viewModel.buupJobSeekerProfile.value!!.verified) {
            binding.ivVerified.setImageResource(R.drawable.ic_verified)
        }


        for ( industry in viewModel.buupJobSeekerProfile.value!!.industry){
            val textView = TextView(context)
            textView.text = industry
            textView.typeface = resources.getFont(R.font.inter_medium)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
            textView.setTextColor(Color.parseColor("#797ED1"))
            textView.elevation = 0.5F
            textView.background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_rectangle_industry_profile )
            textView.setPadding(16,8,16,8)


            binding.industryFlowlayout.addView(textView)
        }

        for ( skill in viewModel.buupJobSeekerProfile.value!!.skills){
            val textView = TextView(context)
            textView.text = skill
            textView.typeface = resources.getFont(R.font.inter_medium)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
            textView.setTextColor(Color.parseColor("#797ED1"))
            textView.elevation = 0.5F
            textView.background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_rectangle_industry_profile )
            textView.setPadding(16,8,16,8)


            binding.skillsFlowlayout.addView(textView)
        }

        for (i in 0..6){
            for (x in 0..2)
            if (viewModel.buupJobSeekerProfile.value!!.availability[i][x]){
                if (x == 0){
                    morningImageViewList[i].setImageResource(R.drawable.ic_availability_morning_checked)
                }
                if (x == 1){
                    afternoonImageViewList[i].setImageResource(R.drawable.ic_availability_sun_checked)
                }
                if (x == 2){
                    afternoonImageViewList[i].setImageResource(R.drawable.ic_availability_night_checked)
                }
            }
        }



//        binding.tvName.text = ParseUser.getCurrentUser().get("FirstName").toString()
//        binding.tvBio.text = ParseUser.getCurrentUser().get("JobTitle").toString()
//        val minSalary = ParseUser.getCurrentUser().get("MinSalary") as Int
//        val maxSalary = ParseUser.getCurrentUser().get("MaxSalary") as Int
//        binding.tvWageValue.text = "$${minSalary} - $${maxSalary} /hr"
//
//        var imageUrl = ParseUser.getCurrentUser().get("ImageUrl").toString()
////            val uri = Uri.parse("https://svkoreans.com/img/svlogo1-1.jpg");
//        if("".equals(imageUrl)){
//            imageUrl = "https://svkoreans.com/img/svlogo1-1.jpg";
//        }
        var imageUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cGVyc29ufGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80";
        Glide.with(requireContext()).load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivProfile)

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

//        binding.rvInterest.adapter = interestListAdapter
//        binding.rvSkills.adapter = skillsListAdapter
//        binding.rvExpList.adapter = experiencesListAdapter
//
//        binding.tvEditProfile.setOnClickListener {
//            val direction : NavDirections = JobSeekerProfileDirections.actionProfileToEditUser()
//            findNavController().navigate(direction)
//
//        }
//
//        binding.tvEditInterest.setOnClickListener{
//            val direction : NavDirections = JobSeekerProfileDirections.actionProfileToEditInterest()
//            findNavController().navigate(direction)
//        }
//
//        binding.tvEditSkills.setOnClickListener{
//            val direction : NavDirections = JobSeekerProfileDirections.actionProfileToEditSkill()
//            findNavController().navigate(direction)
//        }
//
//        binding.tvEditExperience.setOnClickListener{
//            val direction : NavDirections = JobSeekerProfileDirections.actionProfileToEditExperience()
//            findNavController().navigate(direction)
//        }
//

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