package uz.yayra.otabek.data.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import uz.yayra.otabek.common.TodoItem
import uz.yayra.otabek.data.local.LocalStorage
import uz.yayra.otabek.data.local.room.TodoDao
import uz.yayra.otabek.data.repository.TodoItemRepository
import javax.inject.Inject

class TodoItemRepositoryImpl @Inject constructor(private val localStorage: LocalStorage, private val dao: TodoDao) : TodoItemRepository {
    override fun getTodo(): Flow<List<TodoItem>> = dao.getAllTodo().flowOn(Dispatchers.IO)

    override fun getTodoActive(): Flow<List<TodoItem>> = dao.getAllActive().flowOn(Dispatchers.IO)

    override suspend fun insert(data: TodoItem): Boolean = withContext(Dispatchers.IO) {
        dao.insertTodo(data)
        true
    }

    override suspend fun update(data: TodoItem): Boolean = withContext(Dispatchers.IO) {
        dao.update(data)
        true
    }

    override suspend fun delete(data: TodoItem) = withContext(Dispatchers.IO) {
        dao.delete(data)
    }

    override suspend fun checkEye(): Boolean = localStorage.eye

    override suspend fun changeEye() {
        localStorage.eye = !localStorage.eye
    }

    override fun getCompleteCount(): Flow<Int> = dao.getCompleteCount().flowOn(Dispatchers.IO)

    override fun getTheme(): Boolean = localStorage.isDark


    override fun setTheme() {
        localStorage.isDark = !localStorage.isDark
    }
}