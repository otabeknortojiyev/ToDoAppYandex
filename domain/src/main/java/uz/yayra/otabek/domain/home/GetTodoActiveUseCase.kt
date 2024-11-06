package uz.yayra.otabek.domain.home

import kotlinx.coroutines.flow.Flow
import uz.yayra.otabek.common.TodoItem

interface GetTodoActiveUseCase {
    operator fun invoke(): Flow<List<TodoItem>>
}