package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.parsing.HtmlParse
import com.example.myapplication.parsing.XmlParse
import com.example.myapplication.R
import com.example.myapplication.adapters.NewsImgTextAdapter
import com.example.myapplication.classes.SampleData
import com.example.myapplication.classes.XmlData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var data: ArrayList<SampleData>
    private lateinit var parsedData: ArrayList<SampleData>
    private lateinit var xmlData: ArrayList<XmlData>
    private lateinit var adapter: NewsImgTextAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setView()
        updateList()
    }

    private fun setView() {
        setRecyclerView()
        swipe_layout.setOnRefreshListener {
            updateList()
        }
    }

    private fun setRecyclerView() {
        val lm = LinearLayoutManager(this@MainActivity)
        news_list.layoutManager = lm
        news_list.setHasFixedSize(true)
        news_list.setItemViewCacheSize(50)

        data = ArrayList()
        adapter = NewsImgTextAdapter(this, data)
        adapter.newsClick = object : NewsImgTextAdapter.NewsClick {
            override fun onClick(position: Int) {
                makeIntent(position)
            }
        }
        news_list.adapter = adapter
    }

    private fun makeIntent(n: Int) {
        val intentDetail = Intent(applicationContext, DetailActivity::class.java)
        intentDetail.putExtra("title", data[n].title)
        intentDetail.putExtra("link", data[n].link)
        if (data[n].wordList != null) {
            intentDetail.putExtra("wordList", data[n].wordList)
        }
        startActivity(intentDetail)
    }

    private fun updateList() {
        parsedData = ArrayList()
        data.clear()

        runBlocking {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    xmlData = XmlParse().setXmlPullParser()
                }
                    val job = startParse()
                    job.forEach {
                        it.join()
                        adapter.notifyDataSetChanged()
                    }

                if (swipe_layout.isRefreshing) {
                    swipe_layout.isRefreshing = false
                }
                data.addAll(parsedData)
                adapter.notifyDataSetChanged()
            }
        }

    }

    private fun startParse(): ArrayList<Job> {
        val job = arrayListOf<Job>()
        for (i in 0 until xmlData.size) {
            job += lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    parsedData.add(HtmlParse().getMetaProps(xmlData[i]))
                }
            }
        }
        return job
    }
}
