package com.hayroyal.mavericks.mobiledoctor.Helper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hayroyal.mavericks.mobiledoctor.Models.Match
import com.hayroyal.mavericks.mobiledoctor.Models.User
import java.lang.reflect.Type
import java.util.ArrayList

/**
 * Created by mavericks on 4/13/18.
 */
class Conv() {
    inline fun <reified T> m(value : String) : T{
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson(value, type)
    }
}

