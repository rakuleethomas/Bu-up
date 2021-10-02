package org.rakulee.buup.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityOnBoardingBinding


@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    lateinit var binding: ActivityOnBoardingBinding
    var isEmployer: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        binding.lifecycleOwner = this
        binding.activity = this

        binding.switchEmployer.setOnCheckedChangeListener { _, isChecked ->
            isEmployer = isChecked
        }

    }

    fun SignIn(view: View) {
        if (isEmployer) {
            Toast.makeText(this, "Sibal", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun CreateAccount(view: View) {
        if (isEmployer) {
            Toast.makeText(this, "Sibal", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}