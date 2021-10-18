package org.rakulee.buup.fragments.employer.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentEmployerOnBoarding6Binding
import org.rakulee.buup.model.BuupEmployerProfile
import org.rakulee.buup.repo.BuupAPIRepo
import org.rakulee.buup.util.Util
import org.rakulee.buup.util.Util.toHex
import org.rakulee.buup.viewmodel.EmployerOnBoardingViewModel
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployerOnBoarding6.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EmployerOnBoarding6 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    lateinit var buupRepo : BuupAPIRepo
    @Inject
    lateinit var okHttpClient: OkHttpClient

    lateinit var binding : FragmentEmployerOnBoarding6Binding
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_employer_on_boarding6, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.tvSkip.setOnClickListener {
            skip()
        }
        binding.ivNext.setOnClickListener {
            val buupEmployerProfile : BuupEmployerProfile? = viewModel.buupEmployerProfile.value
            buupEmployerProfile!!.companyInfo.address1 = binding.etAddress1.text.toString()
            buupEmployerProfile!!.companyInfo.address2 = binding.etAddress2.text.toString()
            buupEmployerProfile!!.companyInfo.city = binding.etCity.text.toString()
            buupEmployerProfile!!.companyInfo.zipCode = binding.etZipCode.text.toString()
            buupEmployerProfile!!.companyInfo.state = binding.spinnerState.selectedItem.toString()
            viewModel.updateBuupEmployerProfile(buupEmployerProfile)
            doSignUp()
        }
        return binding.root
    }

    fun skip(){
        // skip to next page

        val direction : NavDirections = EmployerOnBoarding6Directions.actionEmployerOnBoarding6ToEmployerLoginActivity()
        findNavController().navigate(direction)
    }

    fun doSignUp() {
        // signup final step.
        CoroutineScope(Dispatchers.IO).launch {
            val buupEmployerProfile : BuupEmployerProfile? = viewModel.buupEmployerProfile.value
            val jsonObject = JsonObject()
            val encryptedPassword = Util.encryptPassword(buupEmployerProfile!!.password.toCharArray())
            val gson = Gson()

            val companyInfo = buupEmployerProfile!!.companyInfo

            jsonObject.addProperty("loginId", buupEmployerProfile.loginId)
            jsonObject.addProperty("password", encryptedPassword.toHex())
            jsonObject.addProperty("timestamp", System.currentTimeMillis())
            jsonObject.add("companyInfo", gson.toJsonTree(companyInfo))
            val jsonString = jsonObject.toString()
            val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
            val result = buupRepo.employerSignUp(requestBody)
            if(result.isSuccessful){
                print(result.body())
            }else{
                print(result.errorBody())
            }
        }

//        val direction : NavDirections = EmployerOnBoarding6Directions.actionEmployerOnBoarding6ToEmployerLoginActivity()
//        findNavController().navigate(direction)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployerOnBoarding6.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerOnBoarding6().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}