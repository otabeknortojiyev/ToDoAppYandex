package uz.yayra.otabek.domain.home

import kotlinx.coroutines.flow.Flow
import uz.yayra.otabek.common.TodoEntity

interface GetTodoUseCase {
    operator fun invoke(network: Boolean, isShow: Boolean): Flow<List<TodoEntity>>
}