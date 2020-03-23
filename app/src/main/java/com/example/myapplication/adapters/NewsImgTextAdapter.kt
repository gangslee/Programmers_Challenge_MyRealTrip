package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.classes.Keyword
import com.example.myapplication.classes.SampleData

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class GlideModule : AppGlideModule()

class NewsImgTextAdapter(private val context: Context, data : ArrayList<SampleData>) :
    RecyclerView.Adapter<NewsImgTextAdapter.ListItemViewHolder>() {

    private val newsData = data
    private lateinit var keywordAdapter : KeywordAdapter

    interface NewsClick{
        fun onClick(position: Int)
    }
    var newsClick : NewsClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_adpter, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsData.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.title?.text = newsData[position].title

        if (newsData[position].imgLink!=null) {
            GlideApp.with(context)
                .load(newsData[position].imgLink)
                .placeholder(R.drawable.mrticon)
                .error(R.drawable.mrticon)
                .centerCrop()
                .into(holder.img as ImageView)
        } else{
            GlideApp.with(context)
                .load(R.drawable.mrticon)
                .centerCrop()
                .into(holder.img as ImageView)
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
        } else{
            holder.desc?.setText(R.string.desc_default)
            holder.list?.visibility = View.GONE
        }
        if(newsClick != null){
            holder.itemView.setOnClickListener {
                newsClick?.onClick(position)
            }
        }
    }

    inner class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img : ImageView? = null
        var title : TextView? = null
        var desc : TextView? = null
        var list : RecyclerView? = null
        init {
            img = itemView.findViewById(R.id.thumbnail) as ImageView
            title = itemView.findViewById(R.id.list_title) as TextView
            desc = itemView.findViewById(R.id.list_desc) as TextView
            list = itemView.findViewById(R.id.keyword_list) as RecyclerView
        }
    }
}
