package uz.yayra.otabek.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val importance: String,
    val isCompleted: Boolean,
    val createdAt: Long,
    val modifiedAt: Long = 0,
    val deadLine: Long = 0,
    val isOffline: Boolean,
    val isInsert: Boolean,
    val isUpdate: Boolean,
    val isDelete: Boolean
)
