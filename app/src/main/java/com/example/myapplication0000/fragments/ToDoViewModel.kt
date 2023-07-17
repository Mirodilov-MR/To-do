package com.example.myapplication0000.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication0000.room.TodoDatabase
import com.example.myapplication0000.room.Contacts
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoRepository
    init {
        val dao = TodoDatabase.getDatabaseInstance(application).todosDao()
        repository = TodoRepository(dao)
    }
    fun addTodos(todo: Contacts) {
        repository.insertTodo(todo)
    }

    fun updateTodos(todo: Contacts){
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }
    fun getAllTodos(): LiveData<List<Contacts>> = repository.getAllTodos()
}
