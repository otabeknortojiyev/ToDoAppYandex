package uz.yayra.otabek.common.request

import com.google.gson.annotations.SerializedName

sealed interface ToDoRequest {

    data class AddToDo(
        val status: String = "ok",
        val element: TodoItemAdd
    ) : ToDoRequest

    data class UpdateToDo(
        val status: String = "ok",
        val element: TodoItemAdd
    ) : ToDoRequest

    data class TodoItemAdd(
        val id: String,
        val text: String,
        val importance: String,
        val deadline: Long,
        val done: Boolean,
        val color: String,
        @SerializedName("created_at") val createdAt: Long,
        @SerializedName("changed_at") val changedAt: Long,
        @SerializedName("last_updated_by") val lastUpdatedBy: String,
    )
}