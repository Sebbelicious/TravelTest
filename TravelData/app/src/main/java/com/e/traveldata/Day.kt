package com.e.traveldata

import android.widget.Button
import java.text.SimpleDateFormat
import java.util.*


class Day (val id: Int, val vacation: Vacation, val date: String) {
    companion object {
        val TABLE_NAME = "Day"
        val ID = "id"
        val VACATION = "vacation"
        val DATE = "date"
    }
}

/*
// husk vi skal på et tidspunkt bruge:  , val mapLink: Button
class Day(var format_as_string: String, val scrapElements: List<ScrapElement>, val description: String) {
    companion object {
        val TABLE_NAME = "Day"
        val ID = "id"
        val VACATION = "vacation"
        //val NAME = ""
    }


    var cal = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat(format_as_string)
    val date = sdf.format(cal.time)




}*/
