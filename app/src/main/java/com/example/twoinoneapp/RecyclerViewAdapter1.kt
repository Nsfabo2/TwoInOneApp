package com.example.twoinoneapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter1 (private val Results: ArrayList<String>): RecyclerView.Adapter<RecyclerViewAdapter1.ItemViewHolder>() {
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val task = Results[position] //to access different colors

        holder.itemView.apply {
            ItemTV.text=task
        }
    }

    override fun getItemCount(): Int {
        return Results.size
    }

}//end class