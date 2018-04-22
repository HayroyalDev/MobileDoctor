package com.hayroyal.mavericks.mobiledoctor.Models

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by mavericks on 4/5/18.
 */
class DbHelper(var context: Context) {
    val TAG = "DbHelper"
    val DATABASE_NAME = "md.db"
    var helper: SqlHelp? = null
    var db: SQLiteDatabase? = null
    private val DB_PATH = "/data/data/com.hayroyal.mavericks.mobiledoctor/databases/"
    var dbFile: File? = null

    @Throws(SQLException::class)
    fun open(): DbHelper {
        helper = SqlHelp(context)
        db = helper!!.writableDatabase
        return this@DbHelper
    }

    fun getAllFever(): Cursor {
        return db!!.rawQuery("select * from fevers", null)
    }

    inner class SqlHelp(context: Context, private var dbFile: File = File(DB_PATH + DATABASE_NAME)) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

        @Synchronized
        override fun getWritableDatabase(): SQLiteDatabase {
            Log.e(TAG, DB_PATH)
            if (!dbFile.exists()) {
                val db = super.getWritableDatabase()
                copyDataBase(db.path)
            }
            return super.getWritableDatabase()
        }

        @Synchronized
        override fun getReadableDatabase(): SQLiteDatabase {
            if (!dbFile.exists()) {
                val db = super.getReadableDatabase()
                copyDataBase(db.path)
            }
            return super.getReadableDatabase()
        }

        private fun copyDataBase(dbPath: String) {
            try {
                val assestDB = context.assets.open(DATABASE_NAME)
                val appDB = FileOutputStream(dbPath, false)

                val buffer = ByteArray(1024)
                var length: Int = assestDB.read(buffer)
                while (length > 0) {
                    appDB.write(buffer, 0, length)
                    length = assestDB.read(buffer)
                }

                appDB.flush()
                appDB.close()
                assestDB.close()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e(TAG, e.toString())
            }

        }

        override fun onCreate(db: SQLiteDatabase) {

        }

        override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {

        }

        override fun close() {
            db!!.close()
        }

    }
}
