package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.ChangeEyeUseCase
import javax.inject.Inject

class ChangeEyeUseCaseImpl @Inject constructor(private val homeRepository: TodoItemRepository) : ChangeEyeUseCase {
    override fun invoke(): Flow<Boolean> = channelFlow {
        homeRepository.changeEye().apply {
            trySend(true)
        }
        awaitClose()
    }
}