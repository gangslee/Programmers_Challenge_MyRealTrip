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

        lifecycleScope.launch{
            setList()
            withContext(Dispatchers.IO){
                rawData = ArrayList()
                rawData.addAll(XmlParse().setXmlPullParser())
//                XmlParse().getMetaProps(data)
            }
            for (i in 0 until rawData.size){
                withContext(Dispatchers.IO){
                    XmlParse().getMetaProps(rawData[i])
                }
                data.add(rawData[i])
                adapter.notifyDataSetChanged()
            }
            
            println("finishfinishfinishfinish")
        }
    }

    fun setList(){
        data = ArrayList()
        adapter = NewsImgTextAdapter(this, data)
        val lm = LinearLayoutManager(this@MainActivity)
        news_list.layoutManager = lm
        news_list.setHasFixedSize(true)
        news_list.adapter = adapter
    }

    fun refresh(){
        adapter.notifyDataSetChanged()
    }
}
