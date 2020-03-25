package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.KeywordAdapter
import com.example.myapplication.classes.Keyword
import com.example.myapplication.classes.XmlData
import com.mrt.nasca.NascaViewListener
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {
    private var title : String? = null
    private var link : String? = null
    private var wordList : ArrayList<Keyword>? = null
    private lateinit var adapter : KeywordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        chkIntent()
        setView()
    }

    private fun chkIntent(){
        title = intent.getStringExtra("title")
        link = intent.getStringExtra("link")
        if(intent.hasExtra("wordList")){
            wordList = intent.getParcelableArrayListExtra("wordList")
            setRecyclerView()
        } else{
            detail_keyword_list.visibility = View.GONE
        }
    }

    private fun setView(){
        detail_title.text = title

        nasca.apply {
            loadUrl(link)
            listener = object : NascaViewListener() {
                override fun onProgressChanged(progress: Int) {
                    super.onProgressChanged(progress)
                    println("complete $progress")
                }

            }
        }
    }

    private fun setRecyclerView(){
        val lm = LinearLayoutManager(this@DetailActivity)
        lm.orientation =LinearLayoutManager.HORIZONTAL
        detail_keyword_list.layoutManager = lm
        detail_keyword_list.setHasFixedSize(true)
        detail_keyword_list.setItemViewCacheSize(50)

        adapter = KeywordAdapter(this, wordList as ArrayList<Keyword>, false)
        detail_keyword_list.adapter = adapter
    }
}
