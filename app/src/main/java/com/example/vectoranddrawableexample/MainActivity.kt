package com.example.vectoranddrawableexample

import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    val CAMERA_RQ = 102

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

        buttonTaps()
    }

    private fun buttonTaps(){
        val btnCameraPermission = findViewById<Button>(R.id.camera_permission)
        btnCameraPermission.setOnClickListener {
            checkForPermission(android.Manifest.permission.CAMERA, "camera", CAMERA_RQ)
        }
    }

    private fun checkForPermission(permission: String, name: String, requestCode: Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)
                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String){
            if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
            }
        }

        when(requestCode){
            CAMERA_RQ -> innerCheck("camera")
        }

    }

    private fun showDialog(permission: String, name: String, requestCode: Int){
        val builder = AlertDialog.Builder(this)
        builder.apply{
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission Required")
            setPositiveButton("Ok") {dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

}