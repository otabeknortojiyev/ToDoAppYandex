package uz.yayra.otabek.domain.home

import kotlinx.coroutines.flow.Flow

interface CheckEyeUseCase {
    operator fun invoke(): Flow<Boolean>
}