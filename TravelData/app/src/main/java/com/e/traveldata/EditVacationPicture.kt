package com.e.traveldata

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import org.jetbrains.anko.db.BlobParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import org.jetbrains.anko.startActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*



class EditVacationPicture : AppCompatActivity() {

    private var btn: Button? = null
    private var imageview: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    //private var vacationId: Int? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_vacation)

        btn = findViewById<View>(R.id.btn_edit) as Button
        imageview = findViewById<View>(R.id.iv_edit) as ImageView

        val vacationId = intent.extras.getInt("id")
        Toast.makeText(this, "The id is: $vacationId", Toast.LENGTH_SHORT).show()


        //btn!!.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA)
            } else {
                showPictureDialog(vacationId)

            }
            //this.onBackPressed()
        //}

    }


    private fun showPictureDialog(id: Int) {
        val pictureDialog = AlertDialog.Builder(this)
        //Toast.makeText(this, "The id in showPictureDialog is: $id", Toast.LENGTH_SHORT).show()
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary(id)
                1 -> takePhotoFromCamera(id)
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary(id: Int) {
        //Toast.makeText(this, "The id in choosePhotoFromGallery is: $id", Toast.LENGTH_SHORT).show()
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.putExtra("id", id)


        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera(id: Int) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //intent.putExtra("id", id)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, contentURI)
                    val id = intent.extras.getInt("id")
                    //val path = saveImage(bitmap)

                    saveImage(bitmap, id)

                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
                    Log.d("uri noget", contentURI.toString())
                    //imageview!!.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val id = intent.extras.getInt("id")
            //val id = 1


            Toast.makeText(this@EditVacationPicture, "The id is in onActivityForResult: $id", Toast.LENGTH_LONG).show()
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            saveImage(thumbnail, id)
            //imageview!!.setImageBitmap(thumbnail)
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImage(myBitmap: Bitmap, id: Int):String {

        var byteArray = getBytesFromBitmap(myBitmap)

        Toast.makeText(this@EditVacationPicture, "The bitmap is in onActivityForResult: ${myBitmap.toString()}", Toast.LENGTH_LONG).show()

        //Toast.makeText(this@EditVacationPicture, "The id is in onActivityForResult: $id", Toast.LENGTH_LONG).show()
        //Toast.makeText(this@EditVacationPicture, "Thepath is in onActivityForResult: $path", Toast.LENGTH_LONG).show()


        database.use {
            update(Vacation.TABLE_NAME, Vacation.IMAGE to byteArray)
                .whereArgs("id = {id}", "id" to id)
                .exec()
        }


        Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
        Log.d("uri noget", myBitmap.toString())
        //imageview!!.setImageBitmap(myBitmap)


//        val bytes = ByteArrayOutputStream()
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
//        val wallpaperDirectory = File(
//            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
//        // have the object build the directory structure, if needed.
//        Log.d("fee",wallpaperDirectory.toString())
//        if (!wallpaperDirectory.exists())
//        {
//
//            wallpaperDirectory.mkdirs()
//        }
//
//        try
//        {
//            Log.d("heel",wallpaperDirectory.toString())
//            val f = File(wallpaperDirectory, ((Calendar.getInstance()
//                .getTimeInMillis()).toString() + ".jpg"))
//            f.createNewFile()
//            val fo = FileOutputStream(f)
//            fo.write(bytes.toByteArray())
//            MediaScannerConnection.scanFile(this,
//                arrayOf(f.getPath()),
//                arrayOf("image/jpeg"), null)
//            fo.close()
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())
//
//            return f.getAbsolutePath()
//
//        }
//        catch (e1: IOException) {
//            e1.printStackTrace()
//        }
//
        return ""
    }

    fun getBytesFromBitmap(bitmap: Bitmap): ByteArray  {
        var stream = ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }
}
