package com.gera.todolistview

data class TodoItem(
    val id: Int,
    val title: String,
    val isCompleted: Boolean,
    val imageResId: Int
)