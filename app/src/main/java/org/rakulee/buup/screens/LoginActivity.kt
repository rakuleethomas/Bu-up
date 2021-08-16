package org.rakulee.buup.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.parse.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityLoginBinding
import org.rakulee.buup.model.Job


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.activity = this

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

    fun createAccount(view: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
}