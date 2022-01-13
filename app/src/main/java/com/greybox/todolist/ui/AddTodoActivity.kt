package com.greybox.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.greybox.todolist.R
import com.greybox.todolist.data.Todo
import com.greybox.todolist.viewmodel.TodoViewModal
import kotlinx.android.synthetic.main.activity_add_todos.*
import java.text.SimpleDateFormat
import java.util.*

class AddTodoActivity : AppCompatActivity() {


    lateinit var viewModal: TodoViewModal
    var todosID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todos)


        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(TodoViewModal::class.java)


        val todosType = intent.getStringExtra("todosType")
        if (todosType.equals("Edit")) {
            // set data to edit text.
            val todoTitle = intent.getStringExtra("todosTitle")
            val todoDescription = intent.getStringExtra("todosDescription")
            todosID = intent.getIntExtra("todosId", -1)
            idBtn.setText("Update Todo")
            todosName.setText(todoTitle)
            todosDescription.setText(todoDescription)
        } else {
            idBtn.setText("Save Todo")
        }


        idBtn.setOnClickListener {

            val todosTitle = todosName.text.toString()
            val todosDescription = todosDescription.text.toString()
            // to Check the type and then save or update the todos data.
            if (todosType.equals("Edit")) {
                if (todosTitle.isNotEmpty() && todosDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedTodo = Todo(todosTitle, todosDescription, currentDateAndTime)
                    updatedTodo.id = todosID
                    viewModal.updateTodo(updatedTodo)
                    Toast.makeText(this, "Todo List Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (todosTitle.isNotEmpty() && todosDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())

                    // When the string is not empty call method to add todos to room database.
                    viewModal.addTodo(Todo(todosTitle, todosDescription, currentDateAndTime))
                    Toast.makeText(this, "$todosTitle Added", Toast.LENGTH_LONG).show()
                }
            }
            // launch a new activity
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.finish()
        }

    }
}