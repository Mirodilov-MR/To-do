package com.example.myapplication0000.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import com.example.myapplication0000.R
import com.example.myapplication0000.model.ChildExpandable
import com.example.myapplication0000.model.ParentExpandable
import com.example.myapplication0000.room.Contacts

class ExpandableAdapter(
    private val context: Context,
    private val parentList: MutableList<ParentExpandable>,
    private val childList: MutableList<MutableList<Contacts>>
) : BaseExpandableListAdapter() {

    private var listener: OnChildItemClickListener? = null


    override fun getGroupCount(): Int {
        return parentList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return childList[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): Any {
        return parentList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return childList[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView
        if (view == null) {
            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.parent_expandable_row, null)
        }

        val title = view?.findViewById<TextView>(R.id.parentTitle)
        title?.text = parentList[groupPosition].title

        return view!!
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var childView = convertView
        if (childView == null) {
            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            childView = inflater.inflate(R.layout.child_expandable_row, null)

        }


        val childTitle = childView?.findViewById<TextView>(R.id.childTitle)
        childTitle?.text = childList[groupPosition][childPosition].name


        childView?.setOnClickListener {
            listener?.onChildItemClick( childPosition,groupPosition)
        }

        return childView!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }


    fun setOnClickChildItem(listener: OnChildItemClickListener) {
        this.listener = listener
    }

    interface OnChildItemClickListener {
        fun onChildItemClick(childPosition: Int, parentPositon: Int)
    }
}