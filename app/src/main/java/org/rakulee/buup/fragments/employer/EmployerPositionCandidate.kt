package org.rakulee.buup.fragments.employer

import android.graphics.Color
import android.os.Bundle
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
import androidx.navigation.NavArgs
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentEmployerPositionCandidateBinding
import org.rakulee.buup.model.BuupEmployerProfile
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.viewmodel.EmployerViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployerPositionCandidate.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EmployerPositionCandidate : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val viewModel : EmployerViewModel by activityViewModels()

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

    lateinit var binding : FragmentEmployerPositionCandidateBinding
    lateinit var jobSeekerProfile : BuupJobSeekerProfile
    val args : EmployerPositionCandidateArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val gson = Gson()
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_employer_position_candidate, container, false)
        binding.lifecycleOwner = this
        binding.fragment = this
        binding.buupJobSeekerProfile = gson.fromJson<BuupJobSeekerProfile>(args.jobSeekerProfile, BuupJobSeekerProfile::class.java)
        jobSeekerProfile = gson.fromJson<BuupJobSeekerProfile>(args.jobSeekerProfile, BuupJobSeekerProfile::class.java)




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
            if(binding.containerVisibility.visibility == View.VISIBLE){
                binding.containerVisibility.visibility = View.GONE
            } else{
                binding.containerVisibility.visibility = View.VISIBLE
            }
        }
        if (jobSeekerProfile.verified) {
            binding.ivVerified.setImageResource(R.drawable.ic_verified)
        }


        for ( industry in jobSeekerProfile.industry){
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

        for ( skill in jobSeekerProfile.skills){
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

        if(jobSeekerProfile.availability.isNotEmpty()){
            for (i in 0..6){
                for (x in 0..2)
                    if (jobSeekerProfile.availability[i][x]){
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
        }





        return binding.root
    }

    fun startChat(){

        val gson = Gson()
        val jsonObj = gson.toJsonTree(jobSeekerProfile)
        val directions : NavDirections = EmployerPositionCandidateDirections.actionEmployerPositionCandidateToMainEmpMessage(jsonObj.toString())
        findNavController().navigate(directions)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployerPositionCandidate.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerPositionCandidate().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}