package com.hayroyal.mavericks.mobiledoctor.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.hayroyal.mavericks.mobiledoctor.Models.History
import com.hayroyal.mavericks.mobiledoctor.R

/**
 * Created by mavericks on 4/13/18.
 */

/**
 * Created by mavericks on 3/2/18.
 */
class HistoryAdapter(context: Context, histlist: ArrayList<History>) : BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val TAG = "SymptomsAdapter"
    private var symList: ArrayList<History> = histlist
    override fun getView(position: Int, altView: View?, parent: ViewGroup?): View {
        var convertView = altView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.hist_row, null)
            holder = ViewHolder()
            holder.symName = convertView.findViewById(R.id.histName)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.symName!!.text = "Diagnosis - ${symList[position].id}"

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
    }

}