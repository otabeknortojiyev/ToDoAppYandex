package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.yayra.otabek.common.TodoEntity
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.InsertUseCase
import javax.inject.Inject

class InsertUseCaseImpl @Inject constructor(private val todoItemRepository: TodoItemRepository) : InsertUseCase {
    override fun invoke(data: TodoEntity, network: Boolean): Flow<Boolean> = flow {
        todoItemRepository.insert(data, network).onSuccess {
            emit(true)
        }.onFailure {}
    }
}