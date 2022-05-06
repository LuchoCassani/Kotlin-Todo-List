package com.cassani.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cassani.todolist.databinding.ItemTodoBinding


class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>()
  {
    inner class TodoViewHolder(val binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root)

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
          return TodoViewHolder(
              ItemTodoBinding.inflate(
                  LayoutInflater.from(parent.context),
                  parent,
                  false
              )
          )
      }

      fun addTodo(todo: Todo){
          todos.add(todo)
          notifyItemInserted(todos.size - 1)
      }
      fun deleteDoneTodos(){
          todos.removeAll { todo ->
              todo.checked
          }
          notifyDataSetChanged()
      }

      private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked:Boolean){
          if (isChecked){
              tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
          }else{
              tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
          }
      }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.apply {
        val curTodo = todos[position]
            tvTodoTitle.text = curTodo.title
            cbDone.isChecked = curTodo.checked
            toggleStrikeThrough(tvTodoTitle, curTodo.checked)
            cbDone.setOnCheckedChangeListener { _, checked ->
                toggleStrikeThrough(tvTodoTitle, checked )
                curTodo.checked = !curTodo.checked
            }
        }


    }

      override fun getItemCount(): Int {
          return todos.size
      }
}