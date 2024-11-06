package uz.yayra.otabek.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("todo")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val importance: Int,
    val isCompleted: Boolean,
    val createdAt: Long,
    val modifiedAt: Long = 0,
    val deadLine: Long = 0
)
