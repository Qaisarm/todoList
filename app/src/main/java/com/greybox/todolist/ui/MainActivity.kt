package com.greybox.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.greybox.todolist.R
import com.greybox.todolist.adapter.TodoAdapter
import com.greybox.todolist.data.Todo
import com.greybox.todolist.viewmodel.TodoViewModal
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TodoAdapter.TodoClickInterface,
    TodoAdapter.TodoClickDeleteInterface {

    lateinit var viewModal: TodoViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        todoRecycler.layoutManager = LinearLayoutManager(this)


        val todoAdapter = TodoAdapter(this, this, this)

        todoRecycler.adapter = todoAdapter

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[TodoViewModal::class.java]


        viewModal.allTodos.observe(this, Observer { list ->
            list?.let {
                // update list.
                todoAdapter.updateList(it)
            }
        })

        idFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTodoActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }


    override fun onDeleteIconClick(todo: Todo) {
        viewModal.deleteTodo(todo)
        // displaying a toast message
        Toast.makeText(this, "${todo.todoTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onTodoClick(todo: Todo) {
        val intent = Intent(this@MainActivity, AddTodoActivity::class.java)
        intent.putExtra("todosType", "Edit")
        intent.putExtra("todosTitle", todo.todoTitle)
        intent.putExtra("todosDescription", todo.todoDescription)
        intent.putExtra("todosId", todo.id)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }
}