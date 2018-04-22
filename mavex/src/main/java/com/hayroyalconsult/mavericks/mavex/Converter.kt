package com.hayroyalconsult.mavericks.mavex

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by mavericks on 4/13/18.
 */
class Converter(){
    inline fun <reified T> GsonToClass(value: String):T{
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson(value, type)
    }
}