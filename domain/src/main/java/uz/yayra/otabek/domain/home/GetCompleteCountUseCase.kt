package uz.yayra.otabek.domain.home

import kotlinx.coroutines.flow.Flow

interface GetCompleteCountUseCase {
    operator fun invoke(): Flow<Int>
}