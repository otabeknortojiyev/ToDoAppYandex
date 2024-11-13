package uz.yayra.otabek.domain.home

import kotlinx.coroutines.flow.Flow

interface SyncUseCase {
    operator fun invoke(): Flow<Unit>
}