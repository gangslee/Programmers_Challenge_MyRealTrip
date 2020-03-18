package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.SampleData

class NewsImgTextAdapter(private val context: Context, data : ArrayList<SampleData>) :
    RecyclerView.Adapter<NewsImgTextAdapter.ListItemViewHolder>() {

    private val newsData = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_adpter, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsData.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val options = RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context).load(newsData[position].imgLink).apply(options).into(holder.img as ImageView)
        holder.title?.text = newsData[position].title
        holder.desc?.text = newsData[position].desc
    }


    inner class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img : ImageView? = null
        var title : TextView? = null
        var desc : TextView? = null
        init {
            img = itemView.findViewById(R.id.thumbnail) as ImageView
            title = itemView.findViewById(R.id.list_title) as TextView
            desc = itemView.findViewById(R.id.list_desc) as TextView
        }
    }
}
