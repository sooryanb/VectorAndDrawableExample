package com.example.vectoranddrawableexample

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val image = findViewById<ImageView>(R.id.chargerImage)

        image.setBackgroundResource(R.drawable.image_list)
        val animationDrawable : AnimationDrawable = image.background as AnimationDrawable


        button.setOnClickListener {
            animationDrawable.start()
            button.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()
        }

        val cakeImage = findViewById<ImageView>(R.id.imageView)
        cakeImage.setOnClickListener{
            cakeImage.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.withEndAction {
                cakeImage.animate().apply {
                    duration = 1000
                    rotationYBy(3600f)
                }
            }.start()
        }


    }
}