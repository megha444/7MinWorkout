package com.example.a7minworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object{
        private val DATABASE_VERSION=1
        private val DATABASE_NAME= "SevenMinutesWorkout.db"
        private val TABLE_NAME="History"
        private val COLUMN_ID="_id"
        private val COLUMN_COMPLETED_DATE="completed_date"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        //Create table History(_id integer primary key, completed date text)
        val CREATE_EXERCISE_TABLE= ("Create table "+ TABLE_NAME+"("+
                COLUMN_ID+" integer primary key, "+ COLUMN_COMPLETED_DATE+" text)")
        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists "+ TABLE_NAME)
        onCreate(db)
    }


    fun addDate(date: String){
        val values=ContentValues()
        values.put(COLUMN_COMPLETED_DATE, date)

        val db= this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()

    }

    fun getAllCompletedDateList(): ArrayList<String>{

        val list= ArrayList<String>()
        val db= this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        cursor.moveToLast()
        do{
            val dateValue= (cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))
            list.add(dateValue)
        }while(cursor.moveToPrevious())

        cursor.close()

        return list
    }

}















