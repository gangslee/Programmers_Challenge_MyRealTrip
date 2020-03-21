package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.classes.Keyword
import com.example.myapplication.R

class KeywordAdapter(private val context: Context, data : ArrayList<Keyword>):
    RecyclerView.Adapter<KeywordAdapter.ListItemViewHolder>(){

    private val wordData = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordAdapter.ListItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.keyword_adapter, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordData.size
    }

    override fun onBindViewHolder(holder: KeywordAdapter.ListItemViewHolder, position: Int) {
        holder.text?.text = wordData[position].word
    }

    inner class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text : TextView? = null
        init {
            text = itemView.findViewById(R.id.keyword) as TextView
        }
    }


}