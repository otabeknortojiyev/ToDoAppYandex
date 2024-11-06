package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.GetCompleteCountUseCase
import javax.inject.Inject

class GetCompleteCountUseCaseImpl @Inject constructor(private val homeRepository: TodoItemRepository) : GetCompleteCountUseCase {
    override fun invoke(): Flow<Int> = channelFlow {
        homeRepository.getCompleteCount().collect {
            trySend(it)
        }
        awaitClose()
    }
}