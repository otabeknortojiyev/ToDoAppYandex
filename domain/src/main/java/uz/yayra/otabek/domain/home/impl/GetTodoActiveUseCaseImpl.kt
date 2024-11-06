package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import uz.yayra.otabek.common.TodoItem
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.GetTodoActiveUseCase
import javax.inject.Inject

class GetTodoActiveUseCaseImpl @Inject constructor(private val todoItemRepository: TodoItemRepository) : GetTodoActiveUseCase {
    override fun invoke(): Flow<List<TodoItem>> = channelFlow {
        todoItemRepository.getTodoActive().collect{
            trySend(it)
        }
        awaitClose()
    }
}