package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.GetCompleteCountUseCase
import javax.inject.Inject

class GetCompleteCountUseCaseImpl @Inject constructor(private val homeRepository: TodoItemRepository) : GetCompleteCountUseCase {
    override fun invoke(): Flow<Int> = flow {
        homeRepository.getCompleteCount().onSuccess {
            emit(it)
        }.onFailure {}
    }
}