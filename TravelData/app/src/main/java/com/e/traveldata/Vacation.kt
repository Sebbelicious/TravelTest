package com.e.traveldata

import java.nio.file.Path

class Vacation(val id: Int, val country: String, val year: String, val image: ByteArray) { //val days: List<Day>,
    companion object {
        val TABLE_NAME = "Vacation"
        val ID = "id"
        val COUNTRY = "country"
        val YEAR = "year"
        val IMAGE = "image"
        //val NAME = ""
    }
    var test = "testStringInVacation"
    var name = country +" "+ year

}