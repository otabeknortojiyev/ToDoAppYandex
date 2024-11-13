package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.yayra.otabek.common.TodoEntity
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.UpdateUseCase
import javax.inject.Inject

class UpdateUseCaseImpl @Inject constructor(private val todoItemRepository: TodoItemRepository) : UpdateUseCase {
    override fun invoke(data: TodoEntity, network: Boolean): Flow<Boolean> = flow {
        todoItemRepository.update(data, network).onSuccess {
            emit(it)
        }.onFailure {
            emit(false)
        }
    }
}