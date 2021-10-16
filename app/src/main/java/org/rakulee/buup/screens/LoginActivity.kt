package org.rakulee.buup.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.parse.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.rakulee.buup.BaseActivity
import org.rakulee.buup.Configs
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityLoginBinding
import org.rakulee.buup.model.Job
import org.rakulee.buup.model.JobSeekerSignInResponse
import org.rakulee.buup.repo.BuupAPIRepo
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : BaseActivity() {


    @Inject
    lateinit var buupAPIRepo : BuupAPIRepo

    @Inject
    lateinit var okHttpClient : OkHttpClient

    lateinit var binding : ActivityLoginBinding
    var MODE = Configs.BUUP_JOB_SEEKER
    var flag = false


    var blocking = false
    fun testEmployer(view: View){
        val intent = Intent(this, PartTimeEmployerActivity::class.java)
        if(!blocking){
            blocking = true;
            startActivity(intent)
        }

    }

    suspend fun login(){
        val jsonObject = JsonObject()
        jsonObject.addProperty("loginId", binding.etEmail.text.toString())
        jsonObject.addProperty("password", binding.etPassword.text.toString())
        val jsonString = jsonObject.toString()
        val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
        val loginResponse : Response<JobSeekerSignInResponse> = buupAPIRepo.jobSeekerSignIn(requestBody)


        /*
        {
            "loginId": "john.snow@email.com",
            "password": "q1w2e3r4t5"
        }
         */

        // if login is success
        if(loginResponse.isSuccessful){
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                loginResponse.body()!!.message
                val intent = Intent(this@LoginActivity, PartTimeJobSeekerActivity::class.java)
                startActivity(intent)
                this@LoginActivity?.finish()
            }
            Log.d("LOGIN", "login: ${loginResponse.body().toString()}")

        }else{
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(applicationContext, "Incorrect login information!", Toast.LENGTH_SHORT).show()
                Log.d("LOGIN", "login: Error")
            }
        }




    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.activity = this
        binding.flag = flag
        binding.btnLogin.setOnClickListener{

            CoroutineScope(Dispatchers.IO).launch {
                login()
            }
        }

        binding.switchEmployer.setOnCheckedChangeListener { compoundButton, isChecked ->
            flag = !flag
            binding.flag = flag
        }

        /**
         * 이게 잡 포스팅 대략 하는거. 일단 edittext같은거랑 서브밋 버튼 만들어서
         * employer 화면에 이런식으로 만들어서 submit button 누를때 saveinbackground 해주면
         * back4app 서버로 넘어갑니다.
         * Job 클래스는 모델에 있는 클래스 참고 부탁드립니다.
         */

//        val exampleJob  = Job()
//
//        exampleJob.ImageUrl = "ahaha"
//        exampleJob.author = ParseUser.getCurrentUser()
//        exampleJob.companyTitle = "wonkyu's company"
//        exampleJob.jobDescription = "blah blah blah"
//
//        exampleJob.saveInBackground { e ->
//            if (e == null) {
//                // success
//            } else {
//                Toast.makeText(this, "error : " + e.message, Toast.LENGTH_SHORT).show()
//            }
//        }


        /**
         * 이게 query 하는 방법
         */
//        val query = ParseQuery.getQuery<Job>("Job")
//        query.whereContains("ImageUrl", "ahaha")
//        query.findInBackground { jobs, e->
//            if (e == null) {
//                for (job : Job in jobs){
//                    Log.d("LoginActivity", "onCreate: "+job.companyTitle)
//                }
//            } else {
//                Toast.makeText(this, "error : " + e.message, Toast.LENGTH_SHORT).show()
//            }
//        }
    }

//    private fun validateInput() : Boolean{
//
//
//        if("".equals(binding.etUsername.text.toString()) || binding.etUsername.text == null){
//            binding.etUsername.requestFocus()
//            Toast.makeText(this,"Username cannot be empty!", Toast.LENGTH_SHORT).show()
//        }else if("".equals(binding.etPassword.text.toString()) || binding.etPassword.text == null) {
//            binding.etPassword.requestFocus()
//            Toast.makeText(this,"Password cannot be empty!", Toast.LENGTH_SHORT).show()
//        }
//
//
//        return "".equals(binding.etPassword.text.toString()) || "".equals(binding.etUsername.text.toString())
//    }
//
//    fun doLogin(){
//
//        if(validateInput()){
//            return
//        }
//        if(binding.tabLayout.getTabAt(0)!!.isSelected){
//            val intent = Intent(this, PartTimeEmployerActivity::class.java)
//            ParseUser.logInInBackground(binding.etUsername.text!!.toString(), binding.etPassword.text!!.toString(), ({user, e ->
//                if (user != null) {
//                    startActivity(intent)
//                    Toast.makeText(this, "Login as employer", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "user doesn't exists", Toast.LENGTH_SHORT).show()
//                }
//            }))
//        }else if(binding.tabLayout.getTabAt(1)!!.isSelected){
//            val intent = Intent(this, PartTimeJobSeekerActivity::class.java)
//            ParseUser.logInInBackground(binding.etUsername.text!!.toString(), binding.etPassword.text!!.toString(), ({user, e ->
//                if (user != null) {
//                    startActivity(intent)
//                    Toast.makeText(this, "Login as job seeker", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "user doesn't exists", Toast.LENGTH_SHORT).show()
//                }
//            }))
//        }
//    }

    override fun onResume() {
        super.onResume()
        blocking = false;
    }

    fun createAccount(view: View) {

        // if the flag is set, employer mode.
        // if not, it would be job_seeker mode.
        if(flag){
            val intent = Intent(this, EmployerSignupActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

    }


}