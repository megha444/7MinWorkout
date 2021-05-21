package com.example.a7minworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var ll_start:LinearLayout
    private lateinit var ll_bmi: LinearLayout
    private lateinit var ll_history: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ll_start=findViewById(R.id.ll_start)
        ll_start.setOnClickListener {
            val intent= Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        ll_bmi=findViewById(R.id.llBmi)
        ll_bmi.setOnClickListener {
            val intent=Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        ll_history=findViewById(R.id.llHistory)
        ll_history.setOnClickListener {
            val intent=Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}