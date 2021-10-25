package org.rakulee.buup.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.BaseActivity
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityPartTimeEmployerBinding

@AndroidEntryPoint
class PartTimeEmployerActivity : BaseActivity() {
    lateinit var binding : ActivityPartTimeEmployerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_part_time_employer)
        binding.lifecycleOwner = this
        NavigationUI.setupWithNavController(binding.bottomNavigationEmployer, findNavController(R.id.nav_host_employer))

    }
}