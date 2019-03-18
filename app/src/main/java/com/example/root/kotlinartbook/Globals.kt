package com.example.root.kotlinartbook

import android.graphics.Bitmap

class Globals {

    companion object Chosen{

        var chosenImagge: Bitmap? = null

        fun returnImage():Bitmap{
            return chosenImagge!!
        }
    }
}