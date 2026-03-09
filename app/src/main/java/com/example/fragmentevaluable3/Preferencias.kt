package com.example.fragmentevaluable3

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import androidx.appcompat.app.AlertDialog

class Preferencias : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        setupPreferenceSummaries()

        findPreference<Preference>("borrar_datos")?.setOnPreferenceClickListener {
            mostrarDialogoConfirmacion()
            true
        }
    }

    private fun setupPreferenceSummaries() {
        // Actualizar resumen del nombre de usuario
        val nombrePref = findPreference<EditTextPreference>("nombre_usuario")
        nombrePref?.summary = nombrePref?.text ?: "Invitado"

        // Actualizar resumen del email
        val emailPref = findPreference<EditTextPreference>("email_usuario")
        emailPref?.summary = emailPref?.text ?: "usuario@email.com"

        // Actualizar resumen del tamaño de texto
        val tamanoPref = findPreference<ListPreference>("tamano_texto")
        tamanoPref?.summary = getTamanoTextoSummary(tamanoPref.value)

        // Actualizar resumen del género preferido
        val generoPref = findPreference<ListPreference>("genero_preferido")
        generoPref?.summary = getGeneroSummary(generoPref.value)
    }

    private fun getTamanoTextoSummary(value: String?): String {
        return when (value) {
            "pequeno" -> "Texto pequeño"
            "mediano" -> "Texto mediano"
            "grande" -> "Texto grande"
            "extra_grande" -> "Texto extra grande"
            else -> "Mediano"
        }
    }

    private fun getGeneroSummary(value: String?): String {
        val entries = resources.getStringArray(R.array.generos_anime_entries)
        val values = resources.getStringArray(R.array.generos_anime_values)

        val index = values.indexOf(value)
        return if (index >= 0) entries[index] else "Shonen"
    }

    override fun onResume() {
        super.onResume()
        // Registrar listener para cambios en preferencias
        preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        // Quitar listener
        preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "nombre_usuario" -> {
                val nombre = sharedPreferences?.getString(key, "Invitado")
                findPreference<EditTextPreference>(key)?.summary = nombre
            }
            "email_usuario" -> {
                val email = sharedPreferences?.getString(key, "usuario@email.com")
                findPreference<EditTextPreference>(key)?.summary = email
            }
            "tamano_texto" -> {
                val valor = sharedPreferences?.getString(key, "mediano")
                findPreference<ListPreference>(key)?.summary = getTamanoTextoSummary(valor)
                // Aquí podrías aplicar el cambio de tamaño en tiempo real
                aplicarTamanoTexto(valor)
            }
            "genero_preferido" -> {
                val valor = sharedPreferences?.getString(key, "shonen")
                findPreference<ListPreference>(key)?.summary = getGeneroSummary(valor)
            }
            "modo_oscuro" -> {
                val modoOscuro = sharedPreferences?.getBoolean(key, false)
                aplicarModoOscuro(modoOscuro)
            }
        }
    }

    private fun aplicarModoOscuro(modoOscuro: Boolean?) {
        if (modoOscuro == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Toast.makeText(requireContext(), "Modo oscuro activado", Toast.LENGTH_SHORT).show()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Toast.makeText(requireContext(), "Modo claro activado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun aplicarTamanoTexto(tamano: String?) {
        val mensaje = when (tamano) {
            "pequeno" -> "Tamaño de texto: Pequeño"
            "mediano" -> "Tamaño de texto: Mediano"
            "grande" -> "Tamaño de texto: Grande"
            "extra_grande" -> "Tamaño de texto: Extra Grande"
            else -> "Tamaño de texto: Mediano"
        }
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarDialogoConfirmacion() {
        AlertDialog.Builder(requireContext())
            .setTitle("Borrar datos")
            .setMessage("¿Estás seguro de que quieres borrar todas las preferencias guardadas?")
            .setPositiveButton("Sí") { _, _ ->
                borrarTodasLasPreferencias()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun borrarTodasLasPreferencias() {
        val editor = preferenceManager.sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()

        PreferenceManager.setDefaultValues(requireContext(), R.xml.preferences, true)

        setupPreferenceSummaries()

        Toast.makeText(requireContext(), "Preferencias restablecidas", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getPreferencias(sharedPreferences: SharedPreferences?): Map<String, Any> {
            val preferencias = mutableMapOf<String, Any>()

            preferencias["modo_oscuro"] = sharedPreferences?.getBoolean("modo_oscuro", false) ?: false
            preferencias["tamano_texto"] = sharedPreferences?.getString("tamano_texto", "mediano") ?: "mediano"
            preferencias["nombre_usuario"] = sharedPreferences?.getString("nombre_usuario", "Invitado") ?: "Invitado"
            preferencias["email"] = sharedPreferences?.getString("email_usuario", "usuario@email.com") ?: "usuario@email.com"
            preferencias["genero_preferido"] = sharedPreferences?.getString("genero_preferido", "shonen") ?: "shonen"

            return preferencias
        }
    }
}