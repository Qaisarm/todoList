package com.greybox.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.greybox.todolist.data.Todo
import com.greybox.todolist.data.TodoDatabase
import com.greybox.todolist.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModal(application: Application) : AndroidViewModel(application) {

    val allTodos: LiveData<List<Todo>>
    val repository: TodoRepository


    init {
        val dao = TodoDatabase.getDatabase(application).getTodosDao()
        repository = TodoRepository(dao)
        allTodos = repository.allTodos
    }


    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(todo)
    }


    fun updateTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(todo)
    }

    fun addTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(todo)
    }

}