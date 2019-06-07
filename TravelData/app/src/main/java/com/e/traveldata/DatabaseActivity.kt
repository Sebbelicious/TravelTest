package com.e.traveldata

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_database.*
import org.jetbrains.anko.db.select

class DatabaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)
    }

    override fun onResume() {
        super.onResume()
        val dbVacations = database.use {
            select("Vacation").parseList(vacationParser)

        }

        db_vacation_recycler.apply {
            adapter = VacationRecyclerAdapter(dbVacations)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DatabaseActivity)
        }
    }
}