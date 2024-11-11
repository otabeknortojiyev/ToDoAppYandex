package uz.yayra.otabek.domain.useCase.home

import kotlinx.coroutines.flow.Flow
import uz.yayra.otabek.common.TodoItem

interface DeleteUseCase {
    operator fun invoke(data: TodoItem) :Flow<Unit>
}