package com.acorn.testanything.testNested

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.acorn.testanything.R

/**
 * Created by acorn on 2019-05-31.
 */
class NestedAdapter constructor(context: Context) : RecyclerView.Adapter<NestedAdapter.NestedViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedViewHolder {
        return NestedViewHolder(inflater.inflate(R.layout.item_nested, parent, false))
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: NestedViewHolder, position: Int) {
        holder.tv.text = "item $position"
    }

    class NestedViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val iv: ImageView = itemView.findViewById(R.id.nestedItemIv)
        val tv: TextView = itemView.findViewById(R.id.nestedItemTv)
    }
}