package org.rakulee.buup

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.screens.LoginActivity
import org.rakulee.buup.screens.OnBoardingActivity
import org.rakulee.buup.screens.PartTimeEmployerActivity
import org.rakulee.buup.screens.TestModeHelper

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    /**
     * check permissions when the application starts
     */
    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
        permissions.forEach{ actionMap ->
            when(actionMap.key){
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    if(actionMap.value) {
                        doStart()
                    }else{
                        Toast.makeText(applicationContext, "Location permission is required", Toast.LENGTH_SHORT).show()
                        doStart()
                    }
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestMultiplePermissions.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        ))
    }


    fun doStart(){
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}