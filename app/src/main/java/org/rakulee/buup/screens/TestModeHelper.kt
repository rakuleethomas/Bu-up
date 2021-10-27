package org.rakulee.buup.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityTestModeHelperBinding

@AndroidEntryPoint
class TestModeHelper : AppCompatActivity() {
    lateinit var binding : ActivityTestModeHelperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_mode_helper)
    }

    fun gotoEmployer(view : View){
        val intent = Intent(this, EmployerLoginActivity::class.java)
        startActivity(intent)
    }

    fun gotoJobSeeker(view : View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}