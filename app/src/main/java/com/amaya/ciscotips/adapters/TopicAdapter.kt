package com.amaya.ciscotips.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.TextView
import android.view.ViewGroup
import android.view.View

import com.amaya.ciscotips.models.Topic
import com.amaya.ciscotips.R

class TopicAdapter(
        private var items: ArrayList<Topic>,
        private val onItemClick: (Topic) -> Unit
): RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var topic = items[position]
        holder?.txtNameTopic?.text = topic.nameTopic
        holder?.txtDescription?.text = topic.descriptionTopic
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.topic_item, parent, false)


        return ViewHolder(itemView).apply {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val name = items[position]
                    onItemClick(name)
                }
            }
        }
    }


    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        var txtNameTopic: TextView? = null
        var txtDescription: TextView? = null

        init {
            this.txtNameTopic = row.findViewById(R.id.txtNameTopic)
            this.txtDescription = row.findViewById(R.id.txtDescription)
        }
    }
}