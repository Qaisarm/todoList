package com.greybox.todolist.repository

import androidx.lifecycle.LiveData
import com.greybox.todolist.data.Todo
import com.greybox.todolist.data.TodoDao

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: LiveData<List<Todo>> = todoDao.getAllTodo()

    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    // a function to delete todos from database.
    suspend fun delete(todo: Todo){
        todoDao.delete(todo)
    }

    // a function to update todos from database.
    suspend fun update(todo: Todo){
        todoDao.update(todo)
    }
}