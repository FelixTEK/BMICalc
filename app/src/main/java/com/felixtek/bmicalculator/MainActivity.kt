package com.felixtek.bmicalculator

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.NumberFormatException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btnCalc : Button
    lateinit var etHeight : EditText
    lateinit var etWeight : EditText
    lateinit var displayTv : TextView
    lateinit var unitSwitch : ToggleButton
    lateinit var mediaPlayer : MediaPlayer
    var imperial = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnCalc = findViewById(R.id.btn_calc)
        etHeight = findViewById(R.id.et_height)
        etWeight = findViewById(R.id.et_weight)
        displayTv = findViewById(R.id.display_tv)
        unitSwitch = findViewById(R.id.unitswitch_tb)

        btnCalc.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        try {
            var height = etHeight.text.toString().toDouble()
            var weight = etWeight.text.toString().toDouble()
            var result = 0.0
            when (v?.id) {
                R.id.btn_calc -> {
                    unitSwitch.setOnCheckedChangeListener { _, isChecked ->

                        if (isChecked) {
                            imperial = true
                        } else {
                            imperial = false
                        }
                    }
                    if (imperial == true) {
                        result = (weight / (height * height)) * 703
                    } else {
                        result = weight / (height * height)
                    }
                    val formattedresult = String.format("%.2f",result)
                    displayTv.text = "$formattedresult"

                    if (result > 40){
                        if (!this::mediaPlayer.isInitialized){
                            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.obese3)
                        }
                        if (mediaPlayer.isPlaying){
                            mediaPlayer.pause()
                            mediaPlayer.seekTo(0)
                        }
                        mediaPlayer.start()
                    }
                }

            }
        }

        catch(e: NumberFormatException){
            val message = Toast.makeText(this, "Please insert height and/or weight",Toast.LENGTH_LONG)
            message.show()
        }

    }

}
