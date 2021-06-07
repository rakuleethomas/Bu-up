package org.rakulee.buup.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityLoginBinding


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.activity = this

    }


    fun onClickEmployerTest(){
        val intent = Intent(this, PartTimeEmployerActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onClickJobSeekerTest(){
        val intent = Intent(this, PartTimeJobSeekerActivity::class.java)
        startActivity(intent)
        finish()
    }
}