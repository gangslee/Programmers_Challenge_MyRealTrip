package com.example.myapplication.parsing

import com.example.myapplication.classes.Keyword
import com.example.myapplication.classes.SampleData
import com.example.myapplication.classes.WordCompare
import com.example.myapplication.classes.XmlData
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList

class HtmlParse {
    fun getMetaProps(data: XmlData) : SampleData{
        val result = SampleData(data)
        try {
            val doc = Jsoup.connect(data.link).referrer("http://www.google.com").get()
            val ogTags = doc.select("meta[property^=og:]")
            when {
                ogTags.size > 0 ->
                    ogTags.forEachIndexed { index, _ ->
                        val tag = ogTags[index]
                        when (tag.attr("property")) {
                            "og:image" -> result.imgLink = tag.attr("content")
                            "og:description" -> {
                                result.desc = tag.attr("content")
                                result.wordList = getMostKeyword(result.desc as String)
                            }
                        }
                    }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    private val match = Regex("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]")

    private fun getMostKeyword(desc: String): ArrayList<Keyword> {
        val str = match.replace(desc," ")
        val st = StringTokenizer(str, " ")
        val wordList: ArrayList<Keyword> = ArrayList()
        var wordData: Keyword?

        while (st.hasMoreTokens()) {
            val word = st.nextToken()
            if (word.length>1 && !word.contains(" ", true) && !word.contains("\n", true)) {
                var addAble = true
                for (i in 0 until wordList.size) {
                    if (word == wordList[i].word) {
                        wordList[i].cnt += 1
                        addAble = false
                        break
                    }
                }
                if (addAble) {
                    wordData = Keyword()
                    wordData.word = word
                    wordList.add(wordData)
                }
            }
        }
        Collections.sort(wordList,
            WordCompare()
        )
        return if (wordList.size > 3) {
            wordList.dropLast(wordList.size - 3) as ArrayList<Keyword>

        } else {
            wordList
        }
    }
}