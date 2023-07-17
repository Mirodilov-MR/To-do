package com.example.myapplication0000.fragments

import androidx.lifecycle.LiveData
import com.example.myapplication0000.room.ToDoDao
import com.example.myapplication0000.room.Contacts

class TodoRepository(private val dao: ToDoDao) {
    fun getAllTodos(): LiveData<List<Contacts>> {
        return dao.getAllTodos()
    }

    fun insertTodo(todo: Contacts) {
        dao.insertTodo(todo)
    }

    fun updateTodo(todo: Contacts) {
        dao.update(todo)
    }
}
