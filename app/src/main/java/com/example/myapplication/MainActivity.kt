package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.dataClasses.SampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch (Dispatchers.IO) {
            setXmlPullParser()
        }
    }

    private fun setXmlPullParser(){
        val pullParserFactory: XmlPullParserFactory
        try {
            pullParserFactory = XmlPullParserFactory.newInstance()
            val parser = pullParserFactory.newPullParser()
            val inputStream = URL("https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko").openStream()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)

            val sampleDatas = parseXml(parser)
            println("size ${sampleDatas.size}")
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws (XmlPullParserException::class, IOException::class)
    fun parseXml(parser: XmlPullParser):ArrayList<SampleData>{
        val datas : ArrayList<SampleData> = ArrayList()
        var eventType = parser.eventType
        var data : SampleData? = null

        while (eventType != XmlPullParser.END_DOCUMENT){
            val name:String
            when(eventType){
                XmlPullParser.START_TAG -> {
                    name = parser.name
                    if(name == "item"){
                        data = SampleData()
                    } else if(data != null){
                        if(name == "title"){
                            data.title = parser.nextText()
                        } else if(name == "link"){
                            data.link = parser.nextText()
                        }
                    }
                }
                XmlPullParser.END_TAG->{
                    name=parser.name
                    if(name.equals("item", ignoreCase = true) && data!=null){
                        datas.add(data)
                    }
                }
            }
            eventType = parser.next()
        }
        return datas
    }
}
