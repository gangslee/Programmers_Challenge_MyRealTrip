package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.classes.Keyword
import com.example.myapplication.classes.XmlData
import com.mrt.nasca.NascaViewListener
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private var titleAndLink : XmlData? = null
    private var wordList : ArrayList<Keyword>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        chkIntent()
        setView()
    }

    private fun chkIntent(){
        titleAndLink = intent.getParcelableExtra("titleAndLink")
        if(intent.hasExtra("wordList")){
            wordList = intent.getParcelableArrayListExtra("wordList")
        }
    }

    private fun setView(){
        detail_title.text = titleAndLink?.title
        nasca.apply {
            loadUrl(titleAndLink?.link)
            listener = object : NascaViewListener() {
                override fun onProgressChanged(progress: Int) {
                    super.onProgressChanged(progress)
                    println("complete $progress")
                }

            }
        }
    }
}
