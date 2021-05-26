package com.acorn.testanything.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.acorn.testanything.R

/**
 * Created by acorn on 2021/5/21.
 */
class DemoAdapter(context: Context, list: Array<Demo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    var onItemClickListener: ((data: Demo, id: Int) -> Unit)? = null
    private val mDatas: MutableList<Demo>

    init {
        mDatas = expandList(list)
    }

    companion object {
        private const val ITEM_NORMAL = 1
        private const val ITEM_SUB = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_NORMAL) {
            DemoViewHolder(inflater.inflate(R.layout.item_demo, parent, false))
        } else {
            SubDemoViewHolder(inflater.inflate(R.layout.item_sub_demo, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    private fun expandList(list: Array<Demo>): MutableList<Demo> {
        val expandedList = mutableListOf<Demo>()
        var toggleColor = true
        list.forEach {
            it.mainItemBgColor = if (toggleColor) 0 else 1
            toggleColor = !toggleColor
            expandedList.add(it)
        }
        return expandedList
    }

    override fun getItemViewType(position: Int): Int {
        val item = mDatas[position]
        return if (item.isSubItem) ITEM_SUB else ITEM_NORMAL
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
//        logI("onBindViewHolder position:$position, payloads isNotEmpty:${payloads.isNotEmpty()}")
        if (payloads.isEmpty()) { //payloads为空,刷新整体
            (holder as? IDemoViewHolder)?.run {
                bindData(mDatas[position], position)
            }
        } else { //payloads不为空,只刷新箭头
            (holder as? DemoViewHolder)?.toggleArrow(payloads[0] as Boolean)
        }
    }

    private fun toggleSubItems(data: Demo) {
        //position需要自己计算得出,因为并没有notifyItemSetChanged,所以在修改完mDatas后,ViewHolder的binddata并不会全部重新调用
        //导致里面的position是错位的.
        val position = mDatas.indexOf(data)

        if (data.subItems != null) {
            if (data.isExpanded) {
                mDatas.removeAll(data.subItems)
                data.isExpanded = false
                //使用payloads局部刷新
                notifyItemChanged(position, data.isExpanded)
                notifyItemRangeRemoved(position + 1, data.subItems.size)
            } else {
                data.subItems.forEachIndexed { index, demo ->
                    demo.isSubItem = true
                    mDatas.add(position + index + 1, demo)
                }
                data.isExpanded = true
                //使用payloads局部刷新
                notifyItemChanged(position, data.isExpanded)
                notifyItemRangeInserted(position + 1, data.subItems.size)
            }
        }
    }

    inner class DemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        IDemoViewHolder {
        private val titleTv = itemView.findViewById<TextView>(R.id.titleTv)
        private val descriptionTv = itemView.findViewById<TextView>(R.id.descriptionTv)
        private val arrowTv = itemView.findViewById<TextView>(R.id.arrowTv)

        override fun bindData(data: Demo, position: Int) {
            itemView.setBackgroundResource(if (data.mainItemBgColor == 0) R.drawable.selector_item_bg_light else R.drawable.selector_item_bg_dark)
            titleTv.text = data.title
            if (data.subItems != null) {
                toggleArrow(data.isExpanded)
                arrowTv.visibility = View.VISIBLE
            } else {
                arrowTv.visibility = View.GONE
            }
            if (null != data.description) {
                descriptionTv.visibility = View.VISIBLE
                descriptionTv.text = data.description
            } else {
                descriptionTv.visibility = View.GONE
            }

            itemView.setOnClickListener {
                if (data.subItems != null) {
                    toggleSubItems(data)
                } else {
                    val id = data.id ?: position
                    //这里因为notifyinsert/remove的原因,position可能有错位.data是正确的,想获取正确的position,使用mDatas.indexOf(data).
                    onItemClickListener?.invoke(data, id)
                }
            }
        }

        fun toggleArrow(isExpanded: Boolean) {
            arrowTv.text = if (isExpanded) "↑↑↑" else "↓↓↓"
        }
    }

    inner class SubDemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        IDemoViewHolder {
        private val titleTv = itemView.findViewById<TextView>(R.id.titleTv)
        private val descriptionTv = itemView.findViewById<TextView>(R.id.descriptionTv)

        override fun bindData(data: Demo, position: Int) {
            titleTv.text = data.title
            if (null != data.description) {
                descriptionTv.visibility = View.VISIBLE
                descriptionTv.text = data.description
            } else {
                descriptionTv.visibility = View.GONE
            }

            itemView.setOnClickListener {
                val id = data.id ?: position
                onItemClickListener?.invoke(data, id)
            }
        }
    }

    interface IDemoViewHolder {
        fun bindData(data: Demo, position: Int)
    }
}