package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import uz.yayra.otabek.common.TodoItem
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.InsertUseCase
import javax.inject.Inject

class InsertUseCaseImpl @Inject constructor(private val todoItemRepository: TodoItemRepository) : InsertUseCase {
    override fun invoke(data: TodoItem): Flow<Boolean> = channelFlow {
        todoItemRepository.insert(data).apply {
            trySend(this)
        }
        awaitClose()
    }
}