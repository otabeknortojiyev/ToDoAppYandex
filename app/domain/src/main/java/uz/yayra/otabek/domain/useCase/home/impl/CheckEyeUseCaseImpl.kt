package uz.yayra.otabek.domain.useCase.home.impl

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.useCase.home.CheckEyeUseCase
import javax.inject.Inject

class CheckEyeUseCaseImpl @Inject constructor(private val homeRepository: TodoItemRepository) : CheckEyeUseCase {
    override fun invoke(): Flow<Boolean> = channelFlow {
        homeRepository.checkEye().apply {
            trySend(this)
        }
        awaitClose()
    }
}