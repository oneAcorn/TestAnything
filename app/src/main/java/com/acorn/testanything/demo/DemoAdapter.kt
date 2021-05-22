package com.acorn.testanything.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acorn.testanything.R
import kotlinx.android.synthetic.main.item_demo.view.*

/**
 * Created by acorn on 2021/5/21.
 */
class DemoAdapter(context: Context, private val list: Array<Demo>) :
    RecyclerView.Adapter<DemoAdapter.DemoViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    var onItemClickListener: ((data: Demo, id: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder {
        return DemoViewHolder(inflater.inflate(R.layout.item_demo, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bindData(list[position], position)
    }

    inner class DemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val color1 = itemView.context.resources.getColor(R.color.itemColor1)
        private val color2 = itemView.context.resources.getColor(R.color.itemColor2)

        fun bindData(data: Demo, position: Int) {
            itemView.setBackgroundColor(if (position % 2 == 0) color1 else color2)
            itemView.titleTv.text = "$position. ${data.title}"
            if (null != data.description) {
                itemView.descriptionTv.visibility = View.VISIBLE
                itemView.descriptionTv.text = data.description
            } else {
                itemView.descriptionTv.visibility = View.GONE
            }

            itemView.setOnClickListener {
                val id = data.id ?: position
                onItemClickListener?.invoke(data, id)
            }
        }
    }
}