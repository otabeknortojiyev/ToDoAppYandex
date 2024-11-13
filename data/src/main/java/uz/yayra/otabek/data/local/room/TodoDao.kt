package uz.yayra.otabek.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import uz.yayra.otabek.common.TodoEntity

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    fun getAllTodo(): List<TodoEntity>

    @Query("SELECT * FROM todo WHERE isCompleted!=1")
    fun getAllActive(): List<TodoEntity>

    @Insert
    fun insertTodo(data: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todoEntities: List<TodoEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: TodoEntity)

    @Delete
    fun delete(data: TodoEntity)

    @Query("SELECT COUNT (*) FROM todo WHERE isCompleted=1")
    fun getCompleteCount(): Int

    @Query("DELETE FROM todo")
    suspend fun clearAllData()
}