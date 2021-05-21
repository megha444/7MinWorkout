package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.HistoryAdapter

class HistoryActivity : AppCompatActivity() {

    private lateinit var toolbar_history_activity: Toolbar
    private lateinit var tvHistory: TextView
    private lateinit var rvHistroy: RecyclerView
    private lateinit var tvNoDataAvailable: TextView
    private val VERTICAL: Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        toolbar_history_activity=findViewById(R.id.toolbar_history_activity)
        tvHistory=findViewById(R.id.tvHistory)
        rvHistroy=findViewById(R.id.rvHistory)
        tvNoDataAvailable=findViewById(R.id.tvNoDataAvailable)


        setSupportActionBar(toolbar_history_activity)
        val actionBar= supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="History"
        }

        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllCompletedDates()
    }

    private fun getAllCompletedDates(){

        val dbHandler=SqliteOpenHelper(this, null)
        val allCompletedDatesList= dbHandler.getAllCompletedDateList()

        for(i in allCompletedDatesList)
            if(allCompletedDatesList.size>0) {
                tvHistory.visibility = View.VISIBLE
                rvHistroy.visibility = View.VISIBLE
                tvNoDataAvailable.visibility = View.GONE

                val llm = LinearLayoutManager(this)
                //llm.stackFromEnd = true     // items gravity sticks to bottom
                //llm.reverseLayout = true   // item list sorting (new messages start from the bottom)

                rvHistroy.layoutManager=llm
                val historyAdapter= HistoryAdapter(this, allCompletedDatesList)
                rvHistroy.adapter=historyAdapter
            }
        else{
                tvHistory.visibility = View.GONE
                rvHistroy.visibility = View.GONE
                tvNoDataAvailable.visibility = View.VISIBLE
        }
    }
}