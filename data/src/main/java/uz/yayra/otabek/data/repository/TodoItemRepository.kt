package uz.yayra.otabek.data.repository

import kotlinx.coroutines.flow.Flow
import uz.yayra.otabek.common.TodoItem


interface TodoItemRepository {
    fun getTodo(): Flow<List<TodoItem>>
    fun getTodoActive(): Flow<List<TodoItem>>
    suspend fun insert(data: TodoItem): Boolean
    suspend fun update(data: TodoItem): Boolean
    suspend fun delete(data: TodoItem)
    suspend fun checkEye(): Boolean
    suspend fun changeEye()
    fun getCompleteCount(): Flow<Int>
    fun getTheme(): Boolean
    fun setTheme()
}