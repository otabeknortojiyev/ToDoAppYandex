package uz.yayra.otabek.domain.home

import kotlinx.coroutines.flow.Flow
import uz.yayra.otabek.common.TodoItem

interface UpdateUseCase {
    operator fun invoke(data: TodoItem) : Flow<Boolean>
}