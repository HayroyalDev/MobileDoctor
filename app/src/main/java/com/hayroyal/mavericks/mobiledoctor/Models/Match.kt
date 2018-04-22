package com.hayroyal.mavericks.mobiledoctor.Models

/**
 * Created by mavericks on 4/10/18.
 */
class Match(var _id: Int? = 0, var fever: Fever? = null, var match: Float? = 0.0F) {
    override fun toString(): String {
        return "Match(_id=$_id, fever=$fever, match=$match)"
    }
}