package uz.yayra.otabek.domain.useCase.home

import kotlinx.coroutines.flow.Flow

interface ChangeEyeUseCase {
    operator fun invoke(): Flow<Boolean>
}