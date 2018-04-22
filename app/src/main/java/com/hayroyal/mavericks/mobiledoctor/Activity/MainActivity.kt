package com.hayroyal.mavericks.mobiledoctor.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatCheckBox
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hayroyal.mavericks.mobiledoctor.Adapter.SymptomsAdapter
import com.hayroyal.mavericks.mobiledoctor.Helper.AppPreference
import com.hayroyal.mavericks.mobiledoctor.Helper.Conv
import com.hayroyal.mavericks.mobiledoctor.Models.Symptoms
import com.hayroyal.mavericks.mobiledoctor.Models.User
import com.hayroyal.mavericks.mobiledoctor.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var symAdapter: SymptomsAdapter? = null
    var symList: ArrayList<Symptoms>? = null
    var selectedData: ArrayList<Symptoms> = ArrayList()
    var appPreference: AppPreference? = null
    var session: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "Mobile Doctor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appPreference = AppPreference(this)
        val user = intent.getStringExtra("user")
        if (user != null) {
            val type = object : TypeToken<User>() {}.type
            session = Gson().fromJson<User>(user, type)
        }
        greet.text = "Hello ${session!!.fullname!!.split(" ")}, ${greet.text.toString()}"
        //appPreference!!.setMatch("")
        symList = populateSymptoms()
        symAdapter = SymptomsAdapter(this, symList!!)
        var ls = findViewById<ListView>(R.id.symListview)
        ls.adapter = symAdapter
        ls.onItemClickListener = AdapterView.OnItemClickListener { _, v, position, _ ->
            val syms = ls.getItemAtPosition(position) as Symptoms
            var chk = v.findViewById<AppCompatCheckBox>(R.id.symCheck)
            if (chk.isChecked) {
                symList!![position].isChecked = false
                chk.isChecked = false
                removeFromSelected(syms)
            } else {
                symList!![position].isChecked = true
                chk.isChecked = true
                addToSelected(syms)
            }
        }
        cont_btn.setOnClickListener {
            if (selectedData.size == 0)
                Toast.makeText(this, "Kindly Select One or More Symptoms.", Toast.LENGTH_SHORT).show()
            else {
                val type = object : TypeToken<ArrayList<Symptoms>>() {}.type
                startActivity(Intent(this, DiagnosisActivity::class.java).apply {
                    putExtra("selected", Gson().toJson(selectedData, type))
                    putExtra("session", Gson().toJson(session))
                    putExtra("main", true)
                })
            }
        }
    }

    private fun removeFromSelected(syms: Symptoms) = selectedData.remove(syms)

    private fun addToSelected(syms: Symptoms) = selectedData.add(syms)

    private fun populateSymptoms(): ArrayList<Symptoms>? {
        val temp = arrayOf("Sweating", "Coughing", "Vomiting", "Headache",
                "Nausea", "Shaking", "Abdominal Pain", "Diarrhea", "Anemia",
                "Muscle Pain", "Convulsion", "Bloody Stools", "Weakness",
                "Stomach Pain", "Poor Appetite", "Rash", "Fatigue",
                "Constipation", "Chest Pain", "Stupor", "Shortness of Breath",
                "Low-Blood Pressure", "Back Pain", "Eye Sensitivity to Bright Lights",
                "Chills", "Lethargy", "Pain in the Joints", "Weight Loss")
        val symlist = ArrayList<Symptoms>()
        for (i in 0 until temp.size) {
            //Log.e(TAG, temp[i])
            val sms = Symptoms()
            sms.id = i + 1
            sms.name = temp[i]
            symlist.add(sms)
        }
        //Log.e(TAG, symlist.toString())
        return symlist
    }

    private fun other() {
        var sym = arrayOf("Fever", "Chills", "Headache", "Poor Appetite", "Rash", "Stupor", "Low-Blood Pressure", "Eye Sensitivity to Bright Lights", "Muscle Pain")
        var arrayList = ArrayList<Symptoms>()
        for (i in 0 until sym.size) {
            //Log.e(TAG, sym[i])
            val sms = Symptoms()
            sms.name = sym[i]
            arrayList.add(sms)
        }
        Log.e(TAG, arrayList.toString())
        Log.e(TAG, Gson().toJson(arrayList))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.action_history) {
            if (appPreference!!.getMatch() == null) {
                Toast.makeText(this, "No History Yet.", Toast.LENGTH_LONG).show()
            } else {
                startActivity(Intent(this, HistoryActivity::class.java).apply {
                    putExtra("session", Gson().toJson(session))
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
