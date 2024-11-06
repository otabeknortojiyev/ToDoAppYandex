package uz.yayra.otabek.domain.useCase.home.impl

import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.useCase.home.GetThemeUseCase
import javax.inject.Inject

class GetThemeUseCaseImpl @Inject constructor(private val homeRepository: TodoItemRepository): GetThemeUseCase {
    override fun invoke(): Boolean = homeRepository.getTheme()
}