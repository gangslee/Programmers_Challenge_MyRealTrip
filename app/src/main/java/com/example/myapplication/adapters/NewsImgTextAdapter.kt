package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.Keyword
import com.example.myapplication.R
import com.example.myapplication.SampleData

class NewsImgTextAdapter(private val context: Context, data : ArrayList<SampleData>) :
    RecyclerView.Adapter<NewsImgTextAdapter.ListItemViewHolder>() {

    private val newsData = data
    lateinit var keywordAdapter : KeywordAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_adpter, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsData.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.title?.text = newsData[position].title

        val options = RequestOptions().centerCrop()
        if (newsData[position].imgLink!=null) {
            Glide.with(context).load(newsData[position].imgLink).apply(options).into(holder.img as ImageView)
        } else{
            Glide.with(context).load(R.drawable.mrticon).apply(options).into(holder.img as ImageView)
        }

        if (newsData[position].desc != null){
            holder.desc?.text = newsData[position].desc
            if(newsData[position].wordList != null){
                val lm = LinearLayoutManager(context)
                lm.orientation =LinearLayoutManager.HORIZONTAL
                holder.list?.layoutManager = lm
                holder.list?.setHasFixedSize(true)
                keywordAdapter = KeywordAdapter(context, newsData[position].wordList as ArrayList<Keyword>)
                holder.list?.adapter = keywordAdapter
            }



//            when(newsData[position].wordList.size){
//                0->{
//                    holder.kw1?.visibility = View.INVISIBLE
//                    holder.kw2?.visibility = View.INVISIBLE
//                    holder.kw3?.visibility = View.INVISIBLE
//                }
//                1->{
//                    holder.kw1?.text = newsData[position].wordList[0].word
//                    holder.kw2?.visibility = View.INVISIBLE
//                    holder.kw3?.visibility = View.INVISIBLE
//                }
//                2->{
//                    holder.kw1?.text = newsData[position].wordList[0].word
//                    holder.kw2?.text = newsData[position].wordList[1].word
//                    holder.kw3?.visibility = View.INVISIBLE
//                }
//                else->{
//                    holder.kw1?.text = newsData[position].wordList[0].word
//                    holder.kw2?.text = newsData[position].wordList[1].word
//                    holder.kw3?.text = newsData[position].wordList[2].word
//                }
//            }

        } else{
            holder.desc?.setText(R.string.desc_default)

        }

    }

    inner class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img : ImageView? = null
        var title : TextView? = null
        var desc : TextView? = null
        var list : RecyclerView? = null
//        var kw1 : TextView? = null
//        var kw2 : TextView? = null
//        var kw3 : TextView? = null
        init {
            img = itemView.findViewById(R.id.thumbnail) as ImageView
            title = itemView.findViewById(R.id.list_title) as TextView
            desc = itemView.findViewById(R.id.list_desc) as TextView
            list = itemView.findViewById(R.id.keyword_list) as RecyclerView
//            kw1 =  itemView.findViewById(R.id.keyword1) as TextView
//            kw2 =  itemView.findViewById(R.id.keyword2) as TextView
//            kw3 =  itemView.findViewById(R.id.keyword3) as TextView
        }
    }
}
