package uz.yayra.otabek.data.repository

import uz.yayra.otabek.common.TodoEntity


interface TodoItemRepository {
    suspend fun getTodo(network: Boolean, isShow: Boolean): Result<List<TodoEntity>>
    suspend fun insert(data: TodoEntity, network: Boolean): Result<Boolean>
    suspend fun update(data: TodoEntity, network: Boolean): Result<Boolean>
    suspend fun delete(data: TodoEntity, network: Boolean): Result<Unit>
    suspend fun checkEye(): Result<Boolean>
    suspend fun getCompleteCount(): Result<Int>
    suspend fun setTheme(): Result<Unit>
    suspend fun syncData(): Result<Unit>
    fun changeEye()
    fun getTheme(): Boolean
}