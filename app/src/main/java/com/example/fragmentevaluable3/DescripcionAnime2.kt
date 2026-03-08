package com.example.fragmentevaluable3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DescripcionAnime2: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.descripcion_anime2)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}