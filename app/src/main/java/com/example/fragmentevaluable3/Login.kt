package com.example.fragmentevaluable3

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class Login : Fragment() {

    companion object {
        private const val USUARIO_VALIDO = "admin"
        private const val PASSWORD_VALIDO = "1234"
        private const val PREFS_NAME = "LoginPrefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USERNAME = "username"
    }

    private lateinit var etUsuario: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var tvMensaje: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        initViews(view)
        setupListeners()
        checkLoginStatus()

        return view
    }

    private fun initViews(view: View) {
        etUsuario = view.findViewById(R.id.etUsuario)
        etPassword = view.findViewById(R.id.etPassword)
        btnLogin = view.findViewById(R.id.btnLogin)
        tvMensaje = view.findViewById(R.id.tvMensaje)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            intentarLogin()
        }
    }

    private fun checkLoginStatus() {
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            val username = sharedPreferences.getString(KEY_USERNAME, "Usuario")
            mostrarMensajeExito("Sesión ya iniciada como $username")
            navegarAHome()
        }
    }

    private fun intentarLogin() {
        val usuario = etUsuario.text.toString().trim()
        val password = etPassword.text.toString().trim()

        when {
            TextUtils.isEmpty(usuario) -> {
                mostrarError("El nombre de usuario no puede estar vacío")
                etUsuario.requestFocus()
                return
            }
            TextUtils.isEmpty(password) -> {
                mostrarError("La contraseña no puede estar vacía")
                etPassword.requestFocus()
                return
            }
            else -> {
                validarCredenciales(usuario, password)
            }
        }
    }

    private fun validarCredenciales(usuario: String, password: String) {
        if (usuario == USUARIO_VALIDO && password == PASSWORD_VALIDO) {
            loginExitoso(usuario)
        } else {
            mostrarError("Usuario o contraseña incorrectos")
            etPassword.text?.clear()
            etPassword.requestFocus()
        }
    }

    private fun loginExitoso(usuario: String) {
        with(sharedPreferences.edit()) {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USERNAME, usuario)
            apply()
        }

        mostrarMensajeExito("¡Bienvenido $usuario!")
        navegarAHome()
    }

    private fun navegarAHome() {
        btnLogin.postDelayed({
            parentFragmentManager.commit {
                replace(R.id.FrameLayout, Home())
                addToBackStack(null)
            }
        }, 1000)
    }

    private fun mostrarError(mensaje: String) {
        tvMensaje.text = mensaje
        tvMensaje.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
        tvMensaje.visibility = View.VISIBLE
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarMensajeExito(mensaje: String) {
        tvMensaje.text = mensaje
        tvMensaje.setTextColor(resources.getColor(android.R.color.holo_green_dark, null))
        tvMensaje.visibility = View.VISIBLE
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }

    fun logout() {
        with(sharedPreferences.edit()) {
            putBoolean(KEY_IS_LOGGED_IN, false)
            remove(KEY_USERNAME)
            apply()
        }

        mostrarMensajeExito("Sesión cerrada")

        parentFragmentManager.commit {
            replace(R.id.FrameLayout, Login())
        }
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getCurrentUsername(): String {
        return sharedPreferences.getString(KEY_USERNAME, "Usuario") ?: "Usuario"
    }
}