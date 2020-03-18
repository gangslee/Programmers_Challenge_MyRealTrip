package com.example.myapplication

class SampleData {
    lateinit var title : String
    lateinit var link : String
    var imgLink : String = ""
    var desc : String = ""
    var wordList : ArrayList<Keyword> = ArrayList()
}

class Keyword{
    lateinit var word : String
    var cnt : Int = 0
}

class WordCompare : Comparator<Keyword>{
    var ret = 0
    override fun compare(o1: Keyword, o2: Keyword): Int {
        ret = if(o1.cnt < o2.cnt){
            1
        } else if(o1.cnt==o2.cnt){
            if(o1.word > o2.word){
                1
            } else{
                -1
            }
        } else{
            -1
        }
        return ret
    }

}