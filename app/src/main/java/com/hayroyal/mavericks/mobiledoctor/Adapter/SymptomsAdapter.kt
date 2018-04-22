package com.hayroyal.mavericks.mobiledoctor.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.hayroyal.mavericks.mobiledoctor.Models.Symptoms
import com.hayroyal.mavericks.mobiledoctor.R

/**
 * Created by mavericks on 3/2/18.
 */
class SymptomsAdapter(context: Context, symlist: ArrayList<Symptoms>) : BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val TAG = "SymptomsAdapter"
    private var symList: ArrayList<Symptoms> = symlist
    override fun getView(position: Int, altView: View?, parent: ViewGroup?): View {
        var convertView = altView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sym_row, null)
            holder = ViewHolder()
            holder.symName = convertView.findViewById(R.id.symName)
            holder.symCheck = convertView.findViewById(R.id.symCheck)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.symName!!.text = symList[position].name
        holder.symCheck!!.isChecked = symList[position].isChecked

        return convertView!!
    }

    override fun getItem(position: Int): Any {
        return symList[position]
    }

    override fun getItemId(position: Int): Long {
        return symList.size.toLong()
    }

    override fun getCount(): Int {
        return symList.size
    }

    internal class ViewHolder {
        var symName: TextView? = null
        var symCheck: CheckBox? = null
    }

}