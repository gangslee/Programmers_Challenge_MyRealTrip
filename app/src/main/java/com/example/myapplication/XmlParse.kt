package com.example.myapplication

import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class XmlParse {
    fun setXmlPullParser() : ArrayList<SampleData>{
        val pullParserFactory: XmlPullParserFactory
        var sampleDatas : ArrayList<SampleData> = ArrayList()
        try {
            pullParserFactory = XmlPullParserFactory.newInstance()
            val parser = pullParserFactory.newPullParser()
            val inputStream = URL("https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko").openStream()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)

            sampleDatas = parseXml(parser)
            println("assssssssssssssssss")

        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sampleDatas
    }

    //    Parse XML & Set Data List
    @Throws (XmlPullParserException::class, IOException::class)
    fun parseXml(parser: XmlPullParser):ArrayList<SampleData>{
        val datas : ArrayList<SampleData> = ArrayList()
        var eventType = parser.eventType
        var data : SampleData? = null

        while (eventType != XmlPullParser.END_DOCUMENT){
            println("cccccccccccc")
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

    fun getMetaProps(datas: ArrayList<SampleData>){
        for (data in datas) {
            try {
                val doc = Jsoup.connect(data.link).get()
                val ogTags = doc.select("meta[property^=og:]")
                when {
                    ogTags.size > 0 ->
                        ogTags.forEachIndexed { index, _ ->
                            val tag = ogTags[index]
                            when (tag.attr("property")) {
                                "og:image" -> data.imgLink = tag.attr("content")
                                "og:description" -> data.desc = tag.attr("content")
                            }
                        }
                }

                data.wordList = getMostKeyword("안녕 안녕해 안녕 안녕은 안녕해 안녕 안녕")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun getMostKeyword(desc : String) : ArrayList<Keyword>{
        val match = Regex("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]")
        val st = StringTokenizer(desc, " ")
        val wordList : ArrayList<Keyword> = ArrayList()
        var wordData : Keyword? = null

        while(st.hasMoreTokens()){
            val word = match.replace(st.nextToken(), "")
            var addAble = true
            for (i in 0 until wordList.size){
                if(word == wordList[i].word){
                    wordList[i].cnt+=1
                    addAble = false
                    break
                }
            }
            if(addAble){
                wordData = Keyword()
                wordData.word = word
                wordList.add(wordData)
            }
        }
        Collections.sort(wordList, WordCompare())
        return wordList
    }
}