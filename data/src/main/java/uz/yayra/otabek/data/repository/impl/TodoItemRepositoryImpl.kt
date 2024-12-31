package uz.yayra.otabek.data.repository.impl

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.yayra.otabek.common.TodoEntity
import uz.yayra.otabek.common.request.ToDoRequest
import uz.yayra.otabek.data.local.LocalStorage
import uz.yayra.otabek.data.local.room.TodoDao
import uz.yayra.otabek.data.network.HomeApi
import uz.yayra.otabek.data.repository.TodoItemRepository
import javax.inject.Inject

class TodoItemRepositoryImpl @Inject constructor(
  private val localStorage: LocalStorage,
  private val dao: TodoDao,
  private val api: HomeApi,
) : TodoItemRepository {
  private val gson = Gson()

  override suspend fun getTodo(network: Boolean, isShow: Boolean): Result<List<TodoEntity>> =
    withContext(Dispatchers.IO) {
      if (network) {
        val response = api.getAll()
        if (response.isSuccessful && response.body() != null) {
          val todoEntities = response.body()!!.list.map { todoItem ->
            TodoEntity(
              id = todoItem.id.toInt(),
              text = todoItem.text,
              importance = todoItem.importance,
              isCompleted = todoItem.done,
              createdAt = todoItem.createdAt,
              modifiedAt = todoItem.changedAt,
              deadLine = todoItem.deadline,
              isOffline = false,
              isInsert = false,
              isUpdate = false,
              isDelete = false
            )
          }
          dao.clearAllData()
          dao.insertAll(todoEntities)
          localStorage.revision = response.body()!!.revision
          Result.success(if (isShow) dao.getAllTodo() else dao.getAllActive())
        } else if (response.errorBody() != null) {
//                val error = gson.fromJson(response.errorBody()!!.string(), ErrorMessage::class.java)
//                Result.failure(Exception(error.message))
          Result.failure(Exception("Some error"))
        } else {
          Result.failure(Throwable(response.message()))
        }
      } else {
        Result.success(if (isShow) dao.getAllTodo() else dao.getAllActive())
      }
    }

  override suspend fun insert(data: TodoEntity, network: Boolean): Result<Boolean> = withContext(Dispatchers.IO) {
    dao.insertTodo(data)
    if (network) {
      syncData()
    }
    Result.success(true)
  }

  override suspend fun update(data: TodoEntity, network: Boolean): Result<Boolean> = withContext(Dispatchers.IO) {
    dao.update(data)
    if (network) {
      syncData()
    }
    Result.success(true)
  }

  override suspend fun delete(data: TodoEntity, network: Boolean): Result<Unit> = withContext(Dispatchers.IO) {
    dao.update(data)
    if (network) {
      syncData()
    }
    Result.success(Unit)
  }

  override suspend fun checkEye(): Result<Boolean> = withContext(Dispatchers.IO) {
    Result.success(localStorage.eye)
  }

  override fun changeEye() {
    localStorage.eye = !localStorage.eye
  }

  override suspend fun getCompleteCount(): Result<Int> = withContext(Dispatchers.IO) {
    Result.success(dao.getCompleteCount())
  }

  override fun getTheme(): Boolean = localStorage.isDark

  override suspend fun setTheme(): Result<Unit> = withContext(Dispatchers.IO) {
    localStorage.isDark = !localStorage.isDark
    Result.success(Unit)
  }

  override suspend fun syncData(): Result<Unit> = withContext(Dispatchers.IO) {
    dao.getAllTodo().forEach {
      if (it.isInsert) {
        val response = api.add(
          ToDoRequest.AddToDo(
            element = ToDoRequest.TodoItemAdd(
              it.id.toString(),
              it.text,
              it.importance,
              it.deadLine,
              it.isCompleted,
              "#FFFFFF",
              it.createdAt,
              it.modifiedAt,
              "Samsung Galaxy A52"
            )
          )
        )
        if (response.isSuccessful && response.body() != null) {
          localStorage.revision = response.body()!!.revision
        }
      } else if (it.isUpdate) {
        val response = api.update(
          it.id.toString(), ToDoRequest.UpdateToDo(
            element = ToDoRequest.TodoItemAdd(
              it.id.toString(),
              it.text,
              it.importance,
              it.deadLine,
              it.isCompleted,
              "#FFFFFF",
              it.createdAt,
              it.modifiedAt,
              "Samsung Galaxy A52"
            )
          )
        )
        if (response.isSuccessful && response.body() != null) {
          localStorage.revision = response.body()!!.revision
        }
      } else if (it.isDelete) {
        val response = api.delete(it.id.toString())
        if (response.isSuccessful && response.body() != null) {
          localStorage.revision = response.body()!!.revision
        }
      }
    }
    Result.success(Unit)
  }
}