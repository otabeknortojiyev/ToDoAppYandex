package uz.yayra.otabek.domain.useCase.home

import kotlinx.coroutines.flow.Flow
import uz.yayra.otabek.common.TodoItem

interface GetTodoUseCase {
    operator fun invoke(isShow: Boolean): Flow<List<TodoItem>>
}