package com.thallesmarchetti.eshop.Utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.thallesmarchetti.eshop.R
import java.io.IOException

class Glideloader(val context:Context) {

    // load user image in the imageView..
    fun loaduserprofileimage (imageuri:Any,imageview: ImageView){

        try {

            Glide
                .with(context)
                .load(imageuri)
                .centerCrop()
                .placeholder(R.drawable.profile)
                .into(imageview)

        }catch (e:IOException){

           e.printStackTrace()
        }
    }

    fun loadproductimage (imageuri:Any,imageview: ImageView){

        try {

            Glide
                    .with(context)
                    .load(imageuri)
                    .centerCrop()
                    .into(imageview)

        }catch (e:IOException){

            e.printStackTrace()
        }
    }
}