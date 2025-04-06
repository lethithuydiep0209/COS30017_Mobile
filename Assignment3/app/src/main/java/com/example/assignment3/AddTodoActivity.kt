package com.example.assignment3

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


class AddTodoActivity : AppCompatActivity(){
    private lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(TodoViewModel::class.java)

        val title = findViewById<EditText>(R.id.editTitle)
        val description = findViewById<EditText>(R.id.editDescription)
        val checkbox = findViewById<CheckBox>(R.id.checkboxDone)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val isEdit = intent.hasExtra("todo_id")
        if (isEdit) {
            title.setText(intent.getStringExtra("todo_title"))
            description.setText(intent.getStringExtra("todo_description"))
            checkbox.isChecked = intent.getBooleanExtra("todo_done", false)
        }

        btnSave.setOnClickListener {
            val titleText = title.text.toString()
            val descText = description.text.toString()
            val done = checkbox.isChecked

            if (titleText.isNotBlank()) {
                if (isEdit) {
                    val id = intent.getIntExtra("todo_id", 0)
                    viewModel.update(Todo(id, titleText, descText, done))
                } else {
                    viewModel.insert(Todo(title = titleText, description = descText, isDone = done))
                }
                finish()
            }
        }
    }
}
