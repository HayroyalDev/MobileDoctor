package com.hayroyal.mavericks.mobiledoctor.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hayroyal.mavericks.mobiledoctor.Models.Match
import com.hayroyal.mavericks.mobiledoctor.R
import kotlinx.android.synthetic.main.activity_treatment.*
import kotlinx.android.synthetic.main.content_treatment.*

class TreatmentActivity : AppCompatActivity() {

    val TAG = "TreatmentActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var ss = intent.getStringExtra("fever")
        val type = object : TypeToken<Match>() {}.type
        var selected = Gson().fromJson<Match>(ss, type)
        about.text = selected.fever!!.about
        supportActionBar?.title = "Treatment -  ${selected.fever!!.name}"
        var builder = StringBuilder()
        fever_title.text = selected.fever!!.name
        val iterator = selected.fever!!.symptoms!!.iterator()
        iterator.forEach {
            builder.append(it.name + "\n")
        }
        for (i in 0 until selected.fever!!.symptoms!!.size) {

        }
        symptoms.text = builder.toString()
        treatment.text = selected.fever!!.treatment
        Log.e(TAG, selected.fever!!.about.toString())
    }

}
