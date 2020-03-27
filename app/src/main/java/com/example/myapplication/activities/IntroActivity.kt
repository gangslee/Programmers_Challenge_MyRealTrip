package com.example.myapplication.activities

import android.content.Intent
import android.content.pm.PackageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.myapplication.R
import com.example.myapplication.adapters.GlideApp
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    private var delayHandler: Handler? = null
    private val SPLASH_TIME_OUT: Long = 1300

    val runnable: Runnable = Runnable{
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
        delayHandler = Handler()
        delayHandler?.postDelayed(runnable, SPLASH_TIME_OUT)
    }

    override fun onDestroy() {
        if(delayHandler != null){
            delayHandler!!.removeCallbacks(runnable)
        }

        super.onDestroy()
    }

    fun setVersionTxt(){
        val info : PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        val version = info.versionName
        intro_version.append(version)
    }
}
