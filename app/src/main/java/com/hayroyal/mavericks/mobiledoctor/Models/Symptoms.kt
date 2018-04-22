package com.hayroyal.mavericks.mobiledoctor.Models

/**
 * Created by mavericks on 3/2/18.
 */
class Symptoms {
    var id: Int? = null
    var name: String? = null
    var isChecked: Boolean = false
    override fun toString(): String {
        return "Symptoms(id=$id, name=$name)"
    }
}