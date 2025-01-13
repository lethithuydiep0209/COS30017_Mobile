package com.example.week2_

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ánh xạ các View
        val number1 = findViewById<EditText>(R.id.Number1)
        val number2 = findViewById<EditText>(R.id.Number2)
        val resultText = findViewById<TextView>(R.id.txtResult)
        val btnPlus = findViewById<Button>(R.id.btnPlus)
        val btnMinus = findViewById<Button>(R.id.btnMinus)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDivide = findViewById<Button>(R.id.btnDivide)

        // Xử lý các nút bấm
        btnPlus.setOnClickListener {
            val num1 = number1.text.toString().toDouble()
            val num2 = number2.text.toString().toDouble()
            resultText.text = "Result: ${num1 + num2}"
        }

        btnMinus.setOnClickListener {
            val num1 = number1.text.toString().toDouble()
            val num2 = number2.text.toString().toDouble()
            resultText.text = "Result: ${num1 - num2}"
        }

        btnMultiply.setOnClickListener {
            val num1 = number1.text.toString().toDouble()
            val num2 = number2.text.toString().toDouble()
            resultText.text = "Result: ${num1 * num2}"
        }

        btnDivide.setOnClickListener {
            val num1 = number1.text.toString().toDouble()
            val num2 = number2.text.toString().toDouble()
            resultText.text = "Result: ${num1 / num2}"
        }

        // Xử lý hiển thị Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
