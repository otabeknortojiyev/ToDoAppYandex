package uz.yayra.otabek.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uz.yayra.otabek.common.TodoItem

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    fun getAllTodo(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo WHERE isCompleted!=1")
    fun getAllActive(): Flow<List<TodoItem>>

    @Insert
    fun insertTodo(data: TodoItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: TodoItem)

    @Delete
    fun delete(data: TodoItem)

    @Query("SELECT COUNT (*) FROM todo WHERE isCompleted=1")
    fun getCompleteCount(): Flow<Int>
}