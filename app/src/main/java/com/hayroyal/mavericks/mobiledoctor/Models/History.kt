package com.hayroyal.mavericks.mobiledoctor.Models

/**
 * Created by mavericks on 4/11/18.
 */
class History(var id: Long, var user_id: Int?, var match: ArrayList<Match>?) {
    override fun toString(): String {
        return "History(id=$id, user_id=$user_id, match=$match)"
    }
}