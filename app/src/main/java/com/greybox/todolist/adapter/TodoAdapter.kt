package com.greybox.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greybox.todolist.R
import com.greybox.todolist.data.Todo
import kotlinx.android.synthetic.main.todo_list.view.*

class TodoAdapter(
    val context: Context,
    val todoClickDeleteInterface: TodoClickDeleteInterface,
    val todoClickInterface: TodoClickInterface
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    private val allTodo = ArrayList<Todo>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.todo_list,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.itemView.idTodoList.text = allTodo[position].todoTitle

        holder.itemView.idTodoDate.text = "Last Updated : " + allTodo.get(position).timeStamp

        holder.itemView.idtodoDelete.setOnClickListener {

            todoClickDeleteInterface.onDeleteIconClick(allTodo.get(position))
        }


        holder.itemView.setOnClickListener {
            todoClickInterface.onTodoClick(allTodo[position])
        }

    }

    override fun getItemCount(): Int {
        return allTodo.size
    }

    fun updateList(newList: List<Todo>) {
        allTodo.clear()
        allTodo.addAll(newList)
        notifyDataSetChanged()
    }


    interface TodoClickDeleteInterface {

        fun onDeleteIconClick(todo: Todo)
    }

    interface TodoClickInterface {

        fun onTodoClick(todo: Todo)
    }

}