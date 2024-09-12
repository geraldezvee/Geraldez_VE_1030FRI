package com.gera.todolistview

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var listView: ListView
    private lateinit var taskTitleEditText: EditText
    private lateinit var addTaskButton: Button

    private val todos = mutableListOf(
        TodoItem(1, "Default Task", false, R.drawable.icon2),
    )

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        taskTitleEditText = findViewById(R.id.addtsk)
        addTaskButton = findViewById(R.id.addbtn)

        todoAdapter = TodoAdapter(this, todos)
        listView.adapter = todoAdapter

        addTaskButton.setOnClickListener {
            val title = taskTitleEditText.text.toString()
            if (title.isNotBlank()) {
                addTask(title)
                taskTitleEditText.text.clear()
            }
        }
    }

    private fun addTask(title: String) {
        val newId = todos.maxOfOrNull { it.id }?.plus(1) ?: 1
        val newTask = TodoItem(newId, title, false, R.drawable.icon2)
        todos.add(newTask)
        todoAdapter.notifyDataSetChanged()
    }
}
