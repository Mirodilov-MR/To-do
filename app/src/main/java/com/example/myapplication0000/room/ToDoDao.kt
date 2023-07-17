package com.example.myapplication0000.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {
    @Query("Select * from Tododo")
    fun getAllTodos(): LiveData<List<Contacts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: Contacts)

    @Delete
    fun delete(todo: Contacts)

    @Update
    fun update(todo: Contacts)

}
