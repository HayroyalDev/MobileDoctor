package com.hayroyal.mavericks.mobiledoctor.Helper

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hayroyal.mavericks.mobiledoctor.Models.History
import com.hayroyal.mavericks.mobiledoctor.Models.User
import com.hayroyalconsult.mavericks.mavex.Converter

/**
 * Created by mavericks on 4/6/18.
 */
class AppPreference(ctx: Context) {
    var context: Context = ctx
    internal var spref: SharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun clearPreference() {
        val editor = spref.edit()
        editor.putString("users", "").commit()
        editor.putString("match", null)

    }

    fun setSymptoms(value: String) {
        val editor = spref.edit()
        editor.putString("symptoms", value)
    }

    fun getSymptoms(): String {
        return spref.getString("symptoms", null)
    }

    fun setFR(boolean: Boolean) {
        val editor = spref.edit()
        editor.putBoolean("FR", boolean).commit()
    }

    fun getFR(): Boolean {
        return spref.getBoolean("FR", true)
    }

    fun getMatch(): ArrayList<History>? {
        var temp = spref.getString("match", "")
        return if (temp != "") Converter().GsonToClass<ArrayList<History>>(temp) else null
    }

    fun setMatch(match: String) {
        val editor = spref.edit()
        editor.putString("match", match).commit()
    }

    fun getUsers(): ArrayList<User>? {
        var temp = spref.getString("users", "")
        return if (temp != "") Converter().GsonToClass<ArrayList<User>>(temp) else  null
    }

    fun setUsers(value: ArrayList<User>) {
        val editor = spref.edit()
        editor.putString("users", Gson().toJson(value)).commit()
    }
}