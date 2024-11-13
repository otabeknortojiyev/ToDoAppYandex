package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.yayra.otabek.common.TodoEntity
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.DeleteUseCase
import javax.inject.Inject

class DeleteUseCaseImpl @Inject constructor(private val homeRepository: TodoItemRepository) : DeleteUseCase {
    override fun invoke(data: TodoEntity, network: Boolean): Flow<Unit> = flow {
        homeRepository.delete(data, network)
        emit(Unit)
    }
}