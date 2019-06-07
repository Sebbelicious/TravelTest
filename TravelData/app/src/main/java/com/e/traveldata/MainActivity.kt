package com.e.traveldata

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick
//import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity<DatabaseActivity>()

        /*vacation_recycler_view.apply {
            adapter = VacationRecyclerAdapter(sampleVacationList)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }*/

    }
}

class VacationRecyclerAdapter(val vacations: List<Vacation>) :
    RecyclerView.Adapter<VacationRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {



        var btn: Button? = null



        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.vacation_item, parent, false)

        btn = view.findViewById<View>(R.id.btn) as Button
        //val id = view.getTag()


        btn.setOnClickListener {
            val id = view.tag
            parent.context.startActivity<EditVacationPicture>("id" to id)
            this@VacationRecyclerAdapter.notifyDataSetChanged()
        }



        return ViewHolder(view)
    }

    override fun getItemCount() = vacations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.view
        val countryLabel = view.findViewById<TextView>(R.id.country_label)
        val dateLabel = view.findViewById<TextView>(R.id.date_label)
        val testLabel3 = view.findViewById<TextView>(R.id.test_label3)
        val cardTest = view.findViewById<CardView>(R.id.card_view)
        val imageView = view.findViewById<ImageView>(R.id.iv)
        val vacation = vacations[position]
        view.setTag(vacation.id)
        //view.setTag(2, pet.javaClass.name)
        countryLabel.text = vacation.country
        dateLabel.text = vacation.test //.days[0].date.toString()
        testLabel3.text = vacation.name

        //imageView.setImageResource(R.drawable.sun)
        //val resolver = this@MainActivity.getContentResolver()
        //val bitmap = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(vacation.image))
        //imageView.setImageURI(Bitmap.(vacation.image))
//        fun getImageFromBytes(image: ByteArray): Bitmap {
//            return BitmapFactory.decodeByteArray(image, 0, image.size);
//        }
        if (vacation.image != null) {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(vacation.image, 0, vacation.image.size))
        }





    }



    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
