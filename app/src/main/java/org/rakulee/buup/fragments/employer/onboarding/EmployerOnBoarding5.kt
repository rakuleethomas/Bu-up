package org.rakulee.buup.fragments.employer.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentEmployerOnBoarding5Binding
import org.rakulee.buup.model.BuupEmployerProfile
import org.rakulee.buup.viewmodel.EmployerOnBoardingViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployerOnBoarding5.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployerOnBoarding5 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentEmployerOnBoarding5Binding
    val viewModel : EmployerOnBoardingViewModel by activityViewModels()
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_employer_on_boarding5, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.tvSkip.setOnClickListener {
            skip()
        }
        binding.ivNext.setOnClickListener {
            val buupEmployerProfile : BuupEmployerProfile? = viewModel.buupEmployerProfile.value
            val industryList = ArrayList<String>()
            industryList.add(binding.spinnerIndustry.selectedItem.toString())
            buupEmployerProfile!!.companyInfo.industry = industryList
            viewModel.updateBuupEmployerProfile(buupEmployerProfile)
            goNextStep()
        }
        return binding.root
    }

    fun skip(){
        // skip to next page
        val direction : NavDirections = EmployerOnBoarding5Directions.actionEmployerOnBoarding5ToEmployerOnBoarding6()
        findNavController().navigate(direction)
    }

    fun goNextStep() {
        // move on to next step
        // need to save data to server
        val direction : NavDirections = EmployerOnBoarding5Directions.actionEmployerOnBoarding5ToEmployerOnBoarding6()
        findNavController().navigate(direction)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployerOnBoarding5.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerOnBoarding5().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}