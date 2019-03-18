package com.example.root.kotlinartbook

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val artnameArray = ArrayList<String>()

        val artImageArray = ArrayList<Bitmap>()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, artnameArray)

        listview.adapter= arrayAdapter

        try {

            val database = this.openOrCreateDatabase("Arts", Context.MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS arts (name VARCHAR, image BLOB)")

            val cursor = database.rawQuery("SELECT * from arts",null)

            val nameIndex = cursor.getColumnIndex("name")
            val imageIndex = cursor.getColumnIndex("image")
            cursor.moveToFirst()

            while (cursor != null){
                artnameArray.add(cursor.getString(nameIndex))

                val byteArray = cursor.getBlob(imageIndex)
                val image = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)

                artImageArray.add(image)

                cursor.moveToNext()

                arrayAdapter.notifyDataSetChanged()
            }
            cursor?.close()

        }catch (e: Exception){

        }

        listview.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->

            val intent = Intent(applicationContext, Main2Activity::class.java)
            intent.putExtra("name",artnameArray[i])
            intent.putExtra("info","old")

            val chosen = Globals.Chosen
            chosen.chosenImagge = artImageArray[i]

            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater

        menuInflater.inflate(R.menu.add_art, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.add_art){

            val intent = Intent(applicationContext, Main2Activity::class.java)
            intent.putExtra("info","new")
            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }
}
