package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.CheckEyeUseCase
import javax.inject.Inject

class CheckEyeUseCaseImpl @Inject constructor(private val homeRepository: TodoItemRepository) : CheckEyeUseCase {
    override fun invoke(): Flow<Boolean> = flow { emit(homeRepository.checkEye().getOrElse { false }) }
}