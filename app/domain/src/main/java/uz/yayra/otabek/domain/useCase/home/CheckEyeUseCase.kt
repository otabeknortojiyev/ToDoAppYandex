package uz.yayra.otabek.domain.useCase.home

import kotlinx.coroutines.flow.Flow

interface CheckEyeUseCase {
    operator fun invoke(): Flow<Boolean>
}