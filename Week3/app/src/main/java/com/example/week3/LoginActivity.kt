package com.example.week3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Phải tham chiếu đúng layout file

        // Ánh xạ các thành phần từ activity_login.xml
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val tvError = findViewById<TextView>(R.id.tvError)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        // Xử lý nút Login
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username == "admin" && password == "1234") {
                tvError.visibility = TextView.GONE
                Toast.makeText(this,  "Login successful!", Toast.LENGTH_SHORT).show()
            } else {
                tvError.text = "Invalid username or password"
                tvError.visibility = TextView.VISIBLE
            }
        }

        // Xử lý nút Cancel
        btnCancel.setOnClickListener {
            etUsername.text.clear()
            etPassword.text.clear()
            tvError.visibility = TextView.GONE
        }
    }
}
