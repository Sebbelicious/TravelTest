package com.e.traveldata


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DayBaseOpenHelper(context: Context = App.instance) :
    ManagedSQLiteOpenHelper(context, "Trippie", null, 3) {
    companion object {
        val instance by lazy { DayBaseOpenHelper() }

        private var instancex: DayBaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): DayBaseOpenHelper {
            if (instancex == null) {
                instancex = DayBaseOpenHelper(context.getApplicationContext())
            }
            return instancex!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            "Day", true,
            Day.ID to INTEGER + PRIMARY_KEY + UNIQUE,
            Day.VACATION to INTEGER, //+ FOREIGN_KEY("vacation", "Vacation", "id"),
            Day.DATE to TEXT
            //Vacation.NAME to TEXT
        )

        db.insert(
            "Day",
            "id" to 1,
            "vacation" to 1,
            "date" to "maj 2019"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Vacation.TABLE_NAME, true)
        onCreate(db)
    }


}

/*
val Context.database: DayBaseOpenHelper
    get() = DayBaseOpenHelper.instance
*/


val dayParser = rowParser {
        id: Int,
        vacation: Vacation,
        date: String ->

    Day(id, vacation, date)

}
