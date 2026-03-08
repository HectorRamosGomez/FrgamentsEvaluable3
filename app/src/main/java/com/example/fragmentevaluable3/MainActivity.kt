package com.example.fragmentevaluable3

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.fragmentevaluable3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        remplazarFragement(Home())

        binding.nav.setOnItemSelectedListener {
          when(it.itemId){

            R.id.Main -> remplazarFragement(Home())
            R.id.Favoritos -> remplazarFragement(Favoritos())
            R.id.Login -> remplazarFragement(Login())


              else ->{

              }
          }
            true
        }

    }

    val btn1 = findViewById<ImageButton>(R.id.btnAnime1)
    val btn2 = findViewById<ImageButton>(R.id.btnAnime2)
    val btn3 = findViewById<ImageButton>(R.id.btnAnime3)
    val btn4 = findViewById<ImageButton>(R.id.btnAnime4)



    private fun remplazarFragement(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.FrameLayout, fragment)
        fragmentTransaction.commit()
    }
}