package com.example.myapplication.activities

import android.content.Intent
import android.content.pm.PackageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    private var delayHandler: Handler? = null
    private val SPLASH_TIME_OUT: Long = 1300

    private val runnable: Runnable = Runnable{
        if(!isFinishing){
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        setVersionTxt()
        setHandler()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(delayHandler != null){
            delayHandler!!.removeCallbacks(runnable)
        }
    }

    private fun setVersionTxt(){
        val info : PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        val version = info.versionName
        intro_version.append(version)
    }

    private fun setHandler(){
        delayHandler = Handler()
        delayHandler?.postDelayed(runnable, SPLASH_TIME_OUT)
    }
}
