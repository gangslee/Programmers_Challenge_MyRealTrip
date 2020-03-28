package com.example.myapplication.classes

import android.os.Parcel
import android.os.Parcelable

class XmlData{
    var title : String? = null
    var link: String? = null
}

class SampleData(titleAndLink : XmlData){
    var title = titleAndLink.title
    var link = titleAndLink.link
    var imgLink: String? = null
    var desc: String? = null
    var wordList: ArrayList<Keyword>? = null
}

class Keyword() : Parcelable{
    var word : String? = null
    var cnt: Int = 0

    constructor(parcel: Parcel) : this() {
        word = parcel.readString()
        cnt = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(word)
        parcel.writeInt(cnt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Keyword> {
        override fun createFromParcel(parcel: Parcel): Keyword {
            return Keyword(parcel)
        }

        override fun newArray(size: Int): Array<Keyword?> {
            return arrayOfNulls(size)
        }
    }
}

class WordCompare : Comparator<Keyword> {
    var ret = 0
    override fun compare(o1: Keyword, o2: Keyword): Int {
        ret = if (o1.cnt < o2.cnt) {
            1
        } else if (o1.cnt == o2.cnt) {
            if (o1.word as String> o2.word as String) {
                1
            } else {
                -1
            }
        } else {
            -1
        }
        return ret
    }

}