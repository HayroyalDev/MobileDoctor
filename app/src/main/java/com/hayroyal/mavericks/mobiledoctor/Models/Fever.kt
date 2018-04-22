package com.hayroyal.mavericks.mobiledoctor.Models

/**
 * Created by mavericks on 4/10/18.
 */
class Fever(var _id: Int? = 0, var name: String? = "", var symptoms: ArrayList<Symptoms>? = ArrayList(), var treatment: String? = "", var about: String? = " ") {
    override fun toString(): String {
        return "Fever(_id=$_id, name='$name', about=$about, treatment=$treatment)"
    }
}