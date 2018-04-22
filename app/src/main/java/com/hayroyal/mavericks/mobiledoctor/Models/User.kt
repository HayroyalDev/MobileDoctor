package com.hayroyal.mavericks.mobiledoctor.Models

/**
 * Created by mavericks on 4/12/18.
 */
class User {
    var _id: Int? = null
    var fullname: String? = null
    var age = null
    var sex = null
    var dob = null
    var email: String? = null
    var password: String? = null

    override fun toString(): String {
        return "User(_id=$_id, fullname=$fullname, age=$age, sex=$sex, dob=$dob, email=$email, password=$password)"
    }


}