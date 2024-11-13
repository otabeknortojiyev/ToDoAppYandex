package uz.yayra.otabek.domain.home.impl

import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.ChangeEyeUseCase
import javax.inject.Inject

class ChangeEyeUseCaseImpl @Inject constructor(private val homeRepository: TodoItemRepository) : ChangeEyeUseCase {
    override fun invoke() = homeRepository.changeEye()
}