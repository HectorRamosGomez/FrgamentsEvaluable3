package com.example.fragmentevaluable3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fragmentevaluable3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        remplazarFragment(Home())

        binding.nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Main -> remplazarFragment(Home())
                R.id.Login -> remplazarFragment(Login())
                R.id.Preferencias -> remplazarFragment(Preferencias())
            }
            true
        }
    }

    private fun remplazarFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragment)
            .commit()
    }
}