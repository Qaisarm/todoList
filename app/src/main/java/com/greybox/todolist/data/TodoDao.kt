package com.greybox.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    // insert method to add a new entry to our database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo : Todo)

    // delete method to delete todos.
    @Delete
    suspend fun delete(todo: Todo)

    //  query method to read all the todos from database.

    @Query("Select * from todosTable order by id ASC")
    fun getAllTodo(): LiveData<List<Todo>>

    // method to update the todos.
    @Update
    suspend fun update(todo: Todo)
}