package uz.yayra.otabek.domain.useCase.home.impl

import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.useCase.home.SetThemeUseCase
import javax.inject.Inject

class SetThemeUseCaseImpl @Inject constructor(private val homeRepository: TodoItemRepository) : SetThemeUseCase {
    override fun invoke() = homeRepository.setTheme()

}