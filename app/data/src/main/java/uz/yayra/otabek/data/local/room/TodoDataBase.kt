package uz.yayra.otabek.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.yayra.otabek.common.TodoItem

@Database(entities = [TodoItem::class], version = 1)
abstract class TodoDataBase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}