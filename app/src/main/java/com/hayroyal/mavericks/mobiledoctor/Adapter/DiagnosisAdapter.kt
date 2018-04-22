package com.hayroyal.mavericks.mobiledoctor.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import com.hayroyal.mavericks.mobiledoctor.Models.Match
import com.hayroyal.mavericks.mobiledoctor.R

/**
 * Created by mavericks on 4/10/18.
 */
class DiagnosisAdapter(context: Context, var match: ArrayList<Match> = ArrayList()) : BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val TAG = "DiagnosisAdapter"
    override fun getView(position: Int, altView: View?, parent: ViewGroup?): View {
        var convertView = altView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.diag_row, null)
            holder = ViewHolder()
            holder.matchName = convertView.findViewById(R.id.matchName)
            holder.matchProg = convertView.findViewById(R.id.matchProg)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.matchName!!.text = match[position].fever!!.name + "  " + match[position].match!!.toInt() + "%"
        holder.matchProg!!.progress = match[position].match!!.toInt()
        return convertView!!

    }

    override fun getItem(position: Int): Any {
        return match[position]
    }

    override fun getItemId(position: Int): Long {
        return match.size.toLong()
    }

    override fun getCount(): Int {
        return match.size
    }

    internal class ViewHolder {
        var matchName: TextView? = null
        var matchProg: ProgressBar? = null
    }

}