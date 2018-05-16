package com.hayroyal.mavericks.mobiledoctor.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hayroyal.mavericks.mobiledoctor.Adapter.DiagnosisAdapter
import com.hayroyal.mavericks.mobiledoctor.Helper.AppPreference
import com.hayroyal.mavericks.mobiledoctor.Models.*
import com.hayroyal.mavericks.mobiledoctor.R
import kotlinx.android.synthetic.main.activity_diagnosis.*
import java.util.*

class DiagnosisActivity : AppCompatActivity() {

    val TAG = "DiagnosisActivity"
    //var pb : ProgressDialog? = null
    var selected: ArrayList<Symptoms>? = null
    var match: ArrayList<Match>? = null
    var helper: DbHelper? = null
    var adapter: DiagnosisAdapter? = null
    var appPreference: AppPreference? = null
    var matched: ArrayList<Match>? = null
    var session: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnosis)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        helper = DbHelper(this).open()
        val main = intent.getBooleanExtra("main", true)
        if (main) {
            val usertype = object : TypeToken<User>() {}.type
            session = Gson().fromJson(intent.getStringExtra("session"), usertype)
            appPreference = AppPreference(this)
            val type = object : TypeToken<ArrayList<Symptoms>>() {}.type
            selected = Gson().fromJson(intent.getStringExtra("selected"), type)
            matched = MatchSymptoms(selected)
        } else {
            val ss = intent.getStringExtra("match")
            val type = object : TypeToken<ArrayList<Match>>() {}.type
            matched = Gson().fromJson(ss, type)
        }
        val data = match!![0]
        matchName.text = data.fever!!.name
        cont_btn.setOnClickListener {
            startActivity(Intent(this, TreatmentActivity::class.java).apply {
                putExtra("fever", Gson().toJson(data))
            })
        }
//        adapter = DiagnosisAdapter(this, matched!!)
//        val list = findViewById<ListView>(R.id.diagList)
//        list.adapter = adapter
//        list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//            startActivity(Intent(this, TreatmentActivity::class.java).apply {
//                putExtra("fever", Gson().toJson(list.getItemAtPosition(position) as Match))
//            })
//        }
    }

    private fun MatchSymptoms(selected: ArrayList<Symptoms>?): ArrayList<Match>? {
        //Get all Data from the database
        var fevArray = ArrayList<Fever>()
        val res = helper!!.getAllFever()
        val type = object : TypeToken<ArrayList<Symptoms>>() {}.type
        while (res.moveToNext()) {
            val sym: ArrayList<Symptoms>? = Gson().fromJson<ArrayList<Symptoms>>(res.getString(3), type)
            fevArray.add(Fever(res.getInt(0), res.getString(1), sym!!, res.getString(2), res.getString(4)))
        }

        match = ArrayList()
        for (fev in fevArray) {
            var smatch = Match()
            for (i in 0 until selected!!.size) {
                for (j in 0 until fev.symptoms!!.size) {
                    if (selected[i].name == fev.symptoms!![j].name) {
                        smatch._id = fev._id
                        smatch.fever = fev
                        smatch.match = smatch.match!! + 1F
                        break
                    }
                }
            }
            if (smatch.match!! > 0.0F) {
                smatch.match = (smatch.match!! / selected.size) * 100
                match!!.add(smatch)
            }
        }

        return match
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.diag_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.action_save) {
            var temp = appPreference!!.getMatch()
            var dd = Date().time
            if (temp != null) {
                var hist = History(dd, session!!._id, matched)
                Log.e(TAG, hist.toString())
                if (temp.contains(hist)) {
                    Toast.makeText(this, "Duplicate Entry...", Toast.LENGTH_SHORT).show()
                } else {
                    temp.add(hist)
                    Toast.makeText(this, "Diagnosis Has Been Saved.", Toast.LENGTH_SHORT).show()
                    appPreference!!.setMatch(Gson().toJson(temp))
                }
            } else {
                var new: ArrayList<History> = ArrayList()
                new.add(History(dd, session?._id, matched))
                Log.e(TAG, new.toString())
                appPreference!!.setMatch(Gson().toJson(new))
                Toast.makeText(this, "Diagnosis Has Been Saved.", Toast.LENGTH_SHORT).show()
            }

        }

        if (id == R.id.action_history) {
            startActivity(Intent(this, HistoryActivity::class.java).apply {
                putExtra("session", Gson().toJson(session))
            })
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
