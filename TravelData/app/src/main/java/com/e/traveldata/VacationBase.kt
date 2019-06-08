package com.e.traveldata


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import java.lang.IllegalArgumentException

class VacationBaseOpenHelper(context: Context = App.instance) ://
    ManagedSQLiteOpenHelper(context, "Trippie", null, 6) {
    companion object {
        val instance by lazy { VacationBaseOpenHelper() }

        private var instancex: VacationBaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): VacationBaseOpenHelper {
            if (instancex == null) {
                instancex = VacationBaseOpenHelper(context.getApplicationContext())
            }
            return instancex!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {

        // Here you create tables
        //db.dropTable( "Vacation",true)
        db.createTable(
            "Vacation", true,

            Vacation.ID to INTEGER + PRIMARY_KEY + UNIQUE,
            Vacation.COUNTRY to TEXT,
            Vacation.YEAR to TEXT,
            Vacation.IMAGE to BLOB
            //Vacation.NAME to TEXT
        )

        /*db.createTable(
            "Day", true,

            Day.ID to INTEGER + PRIMARY_KEY + UNIQUE,
            Day.VACATION to FOREIGN_KEY("vacation", "Vacation", "id"),
            Day.DATE to TEXT
            //Vacation.NAME to TEXT
        )*/

        /*db.insert(
            "Day",
            "id" to 1,
            "vacation" to 1,
            "date" to "maj 2019"
        )
*/



        /*db.insert(
            Vacation.TABLE_NAME,
            Vacation.ID to 1,
            Vacation.COUNTRY to "USA",
            Vacation.YEAR to "2019"
        )
        db.insert(
            Vacation.TABLE_NAME,
            Vacation.ID to 2,
            Vacation.COUNTRY to "Brasilien",
            Vacation.YEAR to "2019"
        )*/

        db.insert(
            "Vacation",
            "id" to 1,
            "country" to "USA",
            "year" to "2019",
            "image" to ByteArray(0)
        )
        db.insert(
            "Vacation",
            "id" to 2,
            "country" to "Brasilien",
            "year" to "2019",
            "image" to ByteArray(0)
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Vacation.TABLE_NAME, true)
        onCreate(db)
    }


}

val Context.database: VacationBaseOpenHelper
    get() = VacationBaseOpenHelper.instance


val vacationParser = rowParser {
        id: Int,
        country: String,
        year: String,
        image: ByteArray ->

    Vacation(id,country, year, image)

}

//days: List<Day>,