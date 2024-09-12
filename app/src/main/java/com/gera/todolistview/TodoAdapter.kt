package com.gera.todolistview

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class TodoAdapter(private val context: Context, private val todos: MutableList<TodoItem>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val doubleTapDelay: Long = 300
    private var lastClickTime: Long = 0
    private var lastPosition: Int = -1

    override fun getCount(): Int = todos.size

    override fun getItem(position: Int): Any = todos[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ClickableViewAccessibility")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.item_todo, parent, false)

        val todoItem = getItem(position) as TodoItem

        val imageView = view.findViewById<ImageView>(R.id.todo_image)
        val checkBox = view.findViewById<CheckBox>(R.id.todo_checkbox)
        val titleTextView = view.findViewById<TextView>(R.id.todo_title)

        imageView.setImageResource(todoItem.imageResId)
        checkBox.isChecked = todoItem.isCompleted
        titleTextView.text = todoItem.title

        // Handle item double-tap
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val clickTime = System.currentTimeMillis()
                    if (clickTime - lastClickTime < doubleTapDelay && lastPosition == position) {
                        showEditOrDeleteDialog(position)
                    }
                    lastClickTime = clickTime
                    lastPosition = position
                }
            }
            true
        }

        return view
    }

    private fun showEditOrDeleteDialog(position: Int) {
        val todoItem = getItem(position) as TodoItem

        AlertDialog.Builder(context)
            .setTitle("Choose an option")
            .setItems(arrayOf("Edit", "Delete")) { _, which ->
                when (which) {
                    0 -> showEditDialog(position)
                    1 -> deleteTask(position)
                }
            }
            .show()
    }

    private fun showEditDialog(position: Int) {
        val todoItem = getItem(position) as TodoItem
        val editText = EditText(context).apply {
            setText(todoItem.title)
        }

        AlertDialog.Builder(context)
            .setTitle("Edit Task")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = editText.text.toString()
                if (newTitle.isNotBlank()) {
                    todos[position] = todoItem.copy(title = newTitle)
                    notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteTask(position: Int) {
        todos.removeAt(position)
        notifyDataSetChanged()
    }
}
