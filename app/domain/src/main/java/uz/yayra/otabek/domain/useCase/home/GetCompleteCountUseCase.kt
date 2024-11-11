package uz.yayra.otabek.domain.useCase.home

import kotlinx.coroutines.flow.Flow

interface GetCompleteCountUseCase {
    operator fun invoke(): Flow<Int>
}