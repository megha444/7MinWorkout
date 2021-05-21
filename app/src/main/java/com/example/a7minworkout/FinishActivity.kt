package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private lateinit var toolbar_finish_activity: Toolbar
    private lateinit var btnFinish: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)


        toolbar_finish_activity=findViewById(R.id.toolbar_finish_activity)
        btnFinish=findViewById(R.id.btnFinish)

        setSupportActionBar(toolbar_finish_activity)

        val actionbar=supportActionBar
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            finish()
        }

        addDateToDatabase()

    }


    private fun addDateToDatabase(){
        val calendar= Calendar.getInstance()
        val dateTime= calendar.time
        Log.d("DATE: ", ""+dateTime)

        val sdf= SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbHandler=SqliteOpenHelper(this, null)
        dbHandler.addDate(date)
        Log.d("DATE: ", "Date is added")
    }
}














