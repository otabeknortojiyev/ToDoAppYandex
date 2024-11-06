package uz.yayra.otabek.domain.useCase.home.impl

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import uz.yayra.otabek.common.TodoItem
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.useCase.home.DeleteUseCase
import javax.inject.Inject

class DeleteUseCaseImpl @Inject constructor(private val homeRepository: TodoItemRepository) : DeleteUseCase {
    override fun invoke(data: TodoItem): Flow<Unit> = channelFlow {
        homeRepository.delete(data).apply {
            trySend(this)
        }
        awaitClose()
    }
}