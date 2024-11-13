package uz.yayra.otabek.common.response

import com.google.gson.annotations.SerializedName

interface ToDoResponse {
    data class GetAll(
        val status: String, val revision: Int, val list: List<TodoItem>
    ) : ToDoResponse

    data class Post(
        val status: String,
        val revision: Int,
        val element: TodoItem
    ) : ToDoResponse

    data class Update(
        val status: String,
        val revision: Int,
        val element: TodoItem
    ) : ToDoResponse

    data class Delete(
        val status: String,
        val revision: Int,
        val element: TodoItem
    ) : ToDoResponse

    data class TodoItem(
        val text: String,
        val files: String?,
        @SerializedName("created_at") val createdAt: Long,
        @SerializedName("changed_at") val changedAt: Long,
        @SerializedName("last_updated_by") val lastUpdatedBy: String,
        val deadline: Long,
        val id: String,
        val importance: String,
        val done: Boolean,
        val color: String
    )
}