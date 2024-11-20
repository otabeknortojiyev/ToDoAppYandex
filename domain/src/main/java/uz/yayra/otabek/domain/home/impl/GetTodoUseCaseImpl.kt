package uz.yayra.otabek.domain.home.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.yayra.otabek.common.TodoEntity
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.domain.home.GetTodoUseCase
import javax.inject.Inject

class GetTodoUseCaseImpl @Inject constructor(private val todoItemRepository: TodoItemRepository) : GetTodoUseCase {
    override fun invoke(network: Boolean, isShow: Boolean): Flow<List<TodoEntity>> = flow {
        todoItemRepository.getTodo(network, isShow).onSuccess {
            emit(it)
        }.onFailure {}
    }
}