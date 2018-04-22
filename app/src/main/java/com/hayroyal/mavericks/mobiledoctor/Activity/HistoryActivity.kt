package com.hayroyal.mavericks.mobiledoctor.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hayroyal.mavericks.mobiledoctor.Adapter.HistoryAdapter
import com.hayroyal.mavericks.mobiledoctor.Helper.AppPreference
import com.hayroyal.mavericks.mobiledoctor.Models.History
import com.hayroyal.mavericks.mobiledoctor.Models.User
import com.hayroyal.mavericks.mobiledoctor.R
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    var appPref: AppPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Diagnosis History"
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val temp = intent.getStringExtra("session")
        val type = object : TypeToken<User>() {}.type
        var session = Gson().fromJson<User>(temp, type)
        appPref = AppPreference(this)
        var hist_temp = appPref!!.getMatch()
        hist_temp!!.forEach {
            Log.e("TAG", it.user_id.toString())
        }
        var history: ArrayList<History> = ArrayList()
        for (u in hist_temp) {
            if (u.user_id == session._id) {
                history.add(u)
            }
        }
        history.reverse()
        val adapter = HistoryAdapter(this, history)
        val lv = findViewById<ListView>(R.id.history_listview)
        lv.adapter = adapter

        lv.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            var tempp = lv.getItemAtPosition(position) as History
            startActivity(Intent(this, DiagnosisActivity::class.java).apply {
                putExtra("match", Gson().toJson(tempp.match))
                putExtra("session", Gson().toJson(session))
                putExtra("main", false)
            })
            // Log.e("TAG",temp.toString() )
        }
    }

}
