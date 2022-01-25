package org.rakulee.buup.fragments.jobseeker

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentJobSeekerOnBoarding2Binding
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.viewmodel.JobSeekerOnBoardingViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobSeekerOnBoarding2.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class JobSeekerOnBoarding2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val viewModel : JobSeekerOnBoardingViewModel by activityViewModels()
    lateinit var binding : FragmentJobSeekerOnBoarding2Binding

    var selectedArray : MutableList<String> = ArrayList<String>()

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_on_boarding2, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.onBoarding2 = this
        binding.viewModel = viewModel

        binding.fab.setOnClickListener{
            goNext()
        }

        var industryArray : Array<String> = resources.getStringArray(R.array.spinner_employer_industries)
        val density = resources.displayMetrics.scaledDensity


        for (i in 0 until 147){
            val button = Button(context)
            button.text = industryArray[i]
            button.typeface = resources.getFont(R.font.inter_medium)
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10F)
            button.elevation = 0.5F
            button.background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_rectangle_industry )
            button.setPadding(16,8,16,8)
            button.setOnClickListener{
                button.isSelected = !button.isSelected
//                Toast.makeText(context, "you clicked $i", Toast.LENGTH_SHORT).show()
                if (selectedArray.size >= 5 && selectedArray.contains(industryArray[i])){
                    button.background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_rectangle_industry )
                    selectedArray.remove(industryArray[i])
                }
                if(selectedArray.size < 5 && button.isSelected){
                    button.background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_rectangle_industry_clicked )
                    selectedArray.add(industryArray[i])
                } else if (selectedArray.size < 5 && !button.isSelected) {
                    button.background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_rectangle_industry )
                    selectedArray.remove(industryArray[i])
                }
            }
            binding.flowLayout.addView(button)
        }



        return binding.root
    }


    fun goNext(){
        var buupJobSeekerProfile : BuupJobSeekerProfile
        viewModel.buupJobSeekerProfile.observe(viewLifecycleOwner, {
            buupJobSeekerProfile = it
            buupJobSeekerProfile.industry = selectedArray as ArrayList<String>

        })

        val direction : NavDirections = JobSeekerOnBoarding2Directions.actionJobSeekerOnBoarding2ToJobSeekerOnBoarding3()
        findNavController().navigate(direction)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JobSeekerOnBoarding2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JobSeekerOnBoarding2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}