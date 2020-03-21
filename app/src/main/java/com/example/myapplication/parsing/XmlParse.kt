package com.example.myapplication.parsing

import com.example.myapplication.classes.SampleData
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL
import kotlin.collections.ArrayList

class XmlParse {
    fun setXmlPullParser(): ArrayList<SampleData> {
        val pullParserFactory: XmlPullParserFactory
        lateinit var sampleDatas: ArrayList<SampleData>
        try {
            pullParserFactory = XmlPullParserFactory.newInstance()
            val parser = pullParserFactory.newPullParser()
            val inputStream = URL("https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko").openStream()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)

            sampleDatas = parseXml(parser)
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sampleDatas
    }

    //    Parse XML & Set Data List
    @Throws(XmlPullParserException::class, IOException::class)
    fun parseXml(parser: XmlPullParser): ArrayList<SampleData> {
        val datas: ArrayList<SampleData> = ArrayList()
        var eventType = parser.eventType
        var data: SampleData? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val name: String
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    name = parser.name
                    if (name == "item") {
                        data = SampleData()
                    } else if (data != null) {
                        if (name == "title") {
                            data.title = parser.nextText()
                        } else if (name == "link") {
                            val link = parser.nextText()
                            data.link = link
//                            getMetaProps(link, data)
                            datas.add(data)
                        }
                    }
                }
            }
            eventType = parser.next()
        }
        return datas
    }


}