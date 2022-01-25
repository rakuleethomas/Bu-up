package org.rakulee.buup.fragments.jobseeker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
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
import org.rakulee.buup.databinding.FragmentJobSeekerOnBoarding3Binding
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.repo.BuupAPIRepo
import org.rakulee.buup.screens.LoginActivity
import org.rakulee.buup.screens.PartTimeJobSeekerActivity
import org.rakulee.buup.viewmodel.JobSeekerOnBoardingViewModel
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobSeekerOnBoarding3.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class JobSeekerOnBoarding3 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    @Inject
    lateinit var buupRepo : BuupAPIRepo
    @Inject
    lateinit var okHttpClient: OkHttpClient
    val viewModel : JobSeekerOnBoardingViewModel by activityViewModels()
    lateinit var binding : FragmentJobSeekerOnBoarding3Binding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
        permissions.forEach{ actionMap ->
            when(actionMap.key){
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    if(!actionMap.value) {
                        Toast.makeText(requireContext(), "Location permission is required", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_on_boarding3, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.onBoarding3 = this


        requestMultiplePermissions.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        ))


//        binding.btnDone.setOnClickListener{
//            val intent = Intent(context, PartTimeJobSeekerActivity::class.java)
//            startActivity(intent)
//            activity?.finish()
//        }

        binding.fab.setOnClickListener{
            goNext()
        }
        return binding.root
    }


    fun goNext(){
        var buupJobSeekerProfile : BuupJobSeekerProfile
//        val zipCode = binding.etZipCode.text.toString()
        val zipCode = binding.etZipCode.editText!!.text.toString()
        viewModel.buupJobSeekerProfile.observe(viewLifecycleOwner, {
            buupJobSeekerProfile = it
            buupJobSeekerProfile.zipCode = zipCode
        })

        if(zipCode.isEmpty()){
            Toast.makeText(requireContext(), "Zip code is missing", Toast.LENGTH_SHORT).show()
        }else{
            doSignUp()
        }

        val direction : NavDirections = JobSeekerOnBoarding3Directions.actionJobSeekerOnBoarding3ToJobSeekerOnBoarding4()
        findNavController().navigate(direction)
    }

    fun doSignUp(){
        viewModel.buupJobSeekerProfile.observe(viewLifecycleOwner, {
            val buupJobSeekerProfile = it
            val jsonObject = JsonObject()
            val password = buupJobSeekerProfile.password
            val gson = Gson()

            jsonObject.addProperty("email", buupJobSeekerProfile.email)
            jsonObject.addProperty("loginId", buupJobSeekerProfile.loginId)
            jsonObject.addProperty("password", password)
            jsonObject.addProperty("timestamp", System.currentTimeMillis())
            val jsonString = jsonObject.toString()
            val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

        })
    }

    companion object {

        const val REQUEST_CODE_PERMISSION = 1001

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JobSeekerOnBoarding3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JobSeekerOnBoarding3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}