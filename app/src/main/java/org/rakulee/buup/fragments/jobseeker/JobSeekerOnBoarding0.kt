package org.rakulee.buup.fragments.jobseeker

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentJobSeekerOnBoarding0Binding
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.util.Util
import org.rakulee.buup.viewmodel.JobSeekerOnBoardingViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobSeekerOnBoarding0.newInstance] factory method to
 * create an instance of this fragment.
 */


/**
 * JobSeekerOnBoarding0 is navigation home fragment
 * The navigation graph is hosted by JobSeekerOnBoardingActivity.kt
 */

@AndroidEntryPoint
class JobSeekerOnBoarding0 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val viewModel : JobSeekerOnBoardingViewModel by activityViewModels()
    lateinit var binding : FragmentJobSeekerOnBoarding0Binding
    lateinit var mContext : Context

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


//        Parse.initialize(
//            Parse.Configuration.Builder(this)
//                .applicationId("aVQUhSqemZDmI8n0mWYOSPzWBAlPb4YPUCIbtH6Q") // should correspond to Application ID env variable
//                .clientKey("hrcIMfNQZESdJVFY5H3g11rkvQK9d5jRWruyQjfA")  // should correspond to Client key env variable
//                .server("https://parseapi.back4app.com").build());


        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_on_boarding0, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.fragment = this
        mContext = requireContext()
        binding.etPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        return binding.root
    }

    fun goBack(){
        val direction : NavDirections = JobSeekerOnBoarding0Directions.actionJobSeekerOnBoarding0ToLoginActivity2()
        findNavController().navigate(direction)
    }

    fun goNextStep() {
        if(validate()){
            val buupJobSeekerProfile = BuupJobSeekerProfile()
            val plainPassword = binding.etPassword.text.toString()
            val encryptedPassword = Util.encryptPassword(plainPassword)
            buupJobSeekerProfile.email = binding.etEmail.text.toString()
            buupJobSeekerProfile.firstName = binding.etFirstName.text.toString()
            buupJobSeekerProfile.lastName = binding.etLastName.text.toString()
            buupJobSeekerProfile.loginId = binding.etEmail.text.toString()
            buupJobSeekerProfile.password = encryptedPassword!!
            viewModel.updateBuupJobSeekerProfile(buupJobSeekerProfile)

            val direction : NavDirections = JobSeekerOnBoarding0Directions.actionJobSeekerOnBoarding0ToJobSeekerOnBoarding1()
            findNavController().navigate(direction)
        }



    }

    fun validate() : Boolean{
        var result = false
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val password = binding.etPassword.text.toString()
        val rePassword = binding.etRePassword.text.toString()


        val alertDialogBuilder = AlertDialog.Builder(mContext)
        alertDialogBuilder.setTitle("Error")
        alertDialogBuilder.setIcon(R.drawable.bu_up_logo)
        alertDialogBuilder.setCancelable(false)

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){}
        /**
         *  Blank fields validation
         */
        if(email.isEmpty()){binding.etEmail.requestFocus(); return false}
        if(phone.isEmpty()){binding.etPhone.requestFocus(); return false}
        if(firstName.isEmpty()){binding.etPhone.requestFocus(); return false}
        if(lastName.isEmpty()){binding.etLastName.requestFocus(); return false}
        if(password.isEmpty()){binding.etPassword.requestFocus(); return false}
        if(rePassword.isEmpty()){binding.etRePassword.requestFocus(); return false}

        /**
         *  Email forms validation
         */
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            alertDialogBuilder.setMessage("The given email is not a valid email format.\n\nPlease try again")
            alertDialogBuilder.setNeutralButton("Confirm") { dialog, which ->
                binding.etEmail.requestFocus()
            }
            val dialog = alertDialogBuilder.create()
            dialog.show()
            val neutralButton = dialog.getButton(DialogInterface.BUTTON_NEUTRAL)
            neutralButton.setTextColor(ContextCompat.getColor(mContext, R.color.buup_job_seeker))
            return false
        }

        /**
         *  Password & Re-password validation
         */
        if(!password.equals(rePassword)){
            alertDialogBuilder.setMessage("The password information is not identical.\n Please try again")
            alertDialogBuilder.setNeutralButton("Confirm") { dialog, which ->
                binding.etPassword.requestFocus()
            }
            val dialog = alertDialogBuilder.create()
            dialog.show()
            val neutralButton = dialog.getButton(DialogInterface.BUTTON_NEUTRAL)
            neutralButton.setTextColor(ContextCompat.getColor(mContext, R.color.buup_job_seeker))
            return false
        }

        result = true

        return result
    }

    fun showDialog(msg : String){

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JobSeekerOnBoarding0.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JobSeekerOnBoarding0().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}