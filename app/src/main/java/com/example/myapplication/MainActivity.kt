package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.NewsImgTextAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var data : ArrayList<SampleData>
    private lateinit var rawData : ArrayList<SampleData>
    private lateinit var adapter : NewsImgTextAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setView()
        updateList()
    }

    private fun setView(){
        data = ArrayList()
        adapter = NewsImgTextAdapter(this, data)

        swipe_layout.setOnRefreshListener {
            updateList()
        }

        val lm = LinearLayoutManager(this@MainActivity)
        news_list.layoutManager = lm
        news_list.setHasFixedSize(true)
        news_list.setItemViewCacheSize(50)
        news_list.adapter = adapter
    }

    private fun updateList(){
        data.clear()
        adapter.notifyDataSetChanged()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                rawData = ArrayList()
                rawData.addAll(XmlParse().setXmlPullParser())
            }

            for (i in 0 until rawData.size) {
                withContext(Dispatchers.IO) {
                    XmlParse().getMetaProps(rawData[i])
                    data.add(rawData[i])
                }
                adapter.notifyItemInserted(i)
            }

            if(swipe_layout.isRefreshing ) {
                swipe_layout.isRefreshing = false
            }
            println("completecompletecomplete")
        }
    }
}
