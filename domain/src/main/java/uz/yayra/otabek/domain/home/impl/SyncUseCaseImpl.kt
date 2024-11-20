package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.SyncUseCase
import javax.inject.Inject

class SyncUseCaseImpl @Inject constructor(private val todoItemRepository: TodoItemRepository) : SyncUseCase {
    override fun invoke(): Flow<Unit> = flow {
        todoItemRepository.syncData().onSuccess {}.onFailure {}
        emit(Unit)
    }
}