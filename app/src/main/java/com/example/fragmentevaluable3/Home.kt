package com.example.fragmentevaluable3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btn1 = view.findViewById<ImageButton>(R.id.btnAnime1)
        val btn2 = view.findViewById<ImageButton>(R.id.btnAnime2)
        val btn3 = view.findViewById<ImageButton>(R.id.btnAnime3)
        val btn4 = view.findViewById<ImageButton>(R.id.btnAnime4)
        val btn5 = view.findViewById<Button>(R.id.BotonFavoritos)

        btn1.setOnClickListener {
            val intent = Intent(activity, DescripcionAnime1::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            val intent = Intent(activity, DescripcionAnime2::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            val intent = Intent(activity, DescripcionAnime3::class.java)
            startActivity(intent)
        }

        btn4.setOnClickListener {
            val intent = Intent(activity, DescripcionAnime4::class.java)
            startActivity(intent)
        }

        btn5.setOnClickListener {
            val intent = Intent(activity, Favoritos2::class.java)
            startActivity(intent)
        }

        return view


    }
}