package uz.yayra.otabek.domain.home

import kotlinx.coroutines.flow.Flow
import uz.yayra.otabek.common.TodoEntity

interface InsertUseCase {
    operator fun invoke(data: TodoEntity, network: Boolean): Flow<Boolean>
}