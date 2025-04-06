package com.example.assignment3

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos")
    fun getAll(): LiveData<List<Todo>>

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)
}
