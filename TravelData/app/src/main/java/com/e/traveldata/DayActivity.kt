package com.e.traveldata


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import kotlinx.android.synthetic.main.activity_edit_pet.*
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import org.jetbrains.anko.sdk27.coroutines.onClick

class DayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.day)
        val id = 1//intent.extras.getInt("id")
        val day = database.use {
            select(Day.TABLE_NAME)
                .whereArgs("id = {id}", "id" to id)
                .parseSingle(dayParser)
        }
        /*id_text.text = pet.id.toString()
        name_edit.setText(pet.name)

        update_button.onClick {
            // val id = id_text.text.toString().toInt()
            val name = name_edit.text.toString()
            database.use {
                update(Pet.TABLE_NAME, Pet.NAME to name)
                    // .whereArgs("id = {id}", Pair("id", id))
                    // .whereSimple("id = ?", id.toString())
                    .whereArgs("id = {id}", "id" to id)
                    .exec()
            }
            finish()
        }*/
    }
}
