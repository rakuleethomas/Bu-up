package org.rakulee.buup.screens

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.BaseActivity
import org.rakulee.buup.Configs
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivitySignupBinding
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.util.Util


/**
 *
 * SignUp Activity for JobSeekers
 *
 */


@AndroidEntryPoint
class SignupActivity : BaseActivity() {

    lateinit var binding : ActivitySignupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        Parse.initialize(
//            Parse.Configuration.Builder(this)
//                .applicationId("aVQUhSqemZDmI8n0mWYOSPzWBAlPb4YPUCIbtH6Q") // should correspond to Application ID env variable
//                .clientKey("hrcIMfNQZESdJVFY5H3g11rkvQK9d5jRWruyQjfA")  // should correspond to Client key env variable
//                .server("https://parseapi.back4app.com").build());
        Configs.currentThemeMode = Configs.BUUP_ONBOARDING
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding.lifecycleOwner = this
        binding.activity = this
        binding.etPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())


    }

    fun startOnBoarding(view: View) {
        if(validate()){
            val buupJobSeekerProfile = BuupJobSeekerProfile()
            val plainPassword = binding.etPassword.text.toString()
            val encryptedPassword = Util.encryptPassword(plainPassword)
            buupJobSeekerProfile.email = binding.etEmail.text.toString()
            buupJobSeekerProfile.firstName = binding.etFirstName.text.toString()
            buupJobSeekerProfile.lastName = binding.etLastName.text.toString()
            buupJobSeekerProfile.loginId = binding.etEmail.text.toString()
            buupJobSeekerProfile.password = encryptedPassword!!
            // serialize buupJobSeekerProfile
            val gson = Gson()
            val temp = gson.toJson(buupJobSeekerProfile)
            val intent = Intent(this, JobSeekerOnBoardingActivity::class.java)
            intent.putExtra("JobSeekerProfileJson", temp)
            startActivity(intent)
            finish()
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


        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Error")
        alertDialogBuilder.setIcon(R.drawable.bu_up_logo)
        alertDialogBuilder.setCancelable(false)

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
            neutralButton.setTextColor(ContextCompat.getColor(this, R.color.buup_job_seeker))
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
            neutralButton.setTextColor(ContextCompat.getColor(this, R.color.buup_job_seeker))
            return false
        }

        result = true

        return result
    }

    fun goBack(view: android.view.View) {
        super.onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


//    fun doSignup() {
//
//
//        val user = ParseUser()
//        val intent = Intent(this, LoginActivity::class.java)
//
//        with(user) {
//            username = binding.etUsername.text.toString()
//            setPassword(binding.etPassword.text.toString())
//            email = binding.etEmail.text.toString()
//            signUpInBackground { e ->
//                if (e == null) {
//                } else {
//                    Log.e("SignUpActivity - ", e.toString());
//                }
//            }
//
//        }
//        startActivity(intent)
//        Toast.makeText(this, binding.etUsername.text.toString() + " was successfully signed up!" , Toast.LENGTH_SHORT).show()
//    }
}