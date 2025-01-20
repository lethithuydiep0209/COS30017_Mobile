package com.example.week3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            calculate(number1, number2, resultText, "add")
        }

        btnMinus.setOnClickListener {
            calculate(number1, number2, resultText, "subtract")
        }

        btnMultiply.setOnClickListener {
            calculate(number1, number2, resultText, "multiply")
        }

        btnDivide.setOnClickListener {
            calculate(number1, number2, resultText, "divide")
        }

        // Xử lý hiển thị Edge-to-Edge (nếu cần)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Hàm tính toán các phép tính
     */
    private fun calculate(
        number1: EditText,
        number2: EditText,
        resultText: TextView,
        operation: String
    ) {
        try {
            val num1 = number1.text.toString().toDouble()
            val num2 = number2.text.toString().toDouble()

            val result = when (operation) {
                "add" -> num1 + num2
                "subtract" -> num1 - num2
                "multiply" -> num1 * num2
                "divide" -> {
                    if (num2 == 0.0) {
                        throw ArithmeticException("Cannot divide by zero")
                    }
                    num1 / num2
                }
                else -> throw IllegalArgumentException("Invalid operation")
            }

            resultText.text = "Result: $result"
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
        } catch (e: ArithmeticException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
        }
    }
}
