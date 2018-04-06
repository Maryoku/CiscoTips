package com.amaya.ciscotips.adapters

import android.accounts.NetworkErrorException
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amaya.ciscotips.models.Chapter
import com.amaya.ciscotips.R
import com.squareup.picasso.Picasso

class ChapterAdapter(
        private var items: ArrayList<Chapter>,
        private val onItemClick: (Chapter) -> Unit
): RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var chapter = items[position]
        holder?.txtNameChapter?.text = chapter.nameChapter
        holder?.txtDescription?.text = chapter.descriptionChapter
        try {
            Picasso.get().load(chapter.imageUrl).into(holder?.imgChapter)
        }
        catch (e: NetworkErrorException) {
            holder?.imgChapter?.setImageResource(R.drawable.food)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.chapter_item, parent, false)

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


    class ViewHolder(row: View) : RecyclerView.ViewHolder(row){

        var txtNameChapter: TextView? = null
        var txtDescription: TextView? = null
        var imgChapter: ImageView? = null


        init {
            this.txtNameChapter = row.findViewById(R.id.txtNameChapter)
            this.txtDescription = row.findViewById(R.id.txtDescription)
            this.imgChapter = row.findViewById(R.id.imgChapter)
        }
    }
}