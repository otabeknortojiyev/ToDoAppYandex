package uz.yayra.otabek.domain.useCase.home.impl

import kotlinx.coroutines.flow.Flow
import uz.yayra.otabek.common.TodoItem
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.useCase.home.GetTodoUseCase
import javax.inject.Inject

class GetTodoUseCaseImpl @Inject constructor(private val todoItemRepository: TodoItemRepository) : GetTodoUseCase {
    override fun invoke(isShow: Boolean): Flow<List<TodoItem>> = if (isShow) todoItemRepository.getTodo() else todoItemRepository.getTodoActive()
}