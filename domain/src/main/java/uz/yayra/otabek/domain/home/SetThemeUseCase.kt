package uz.yayra.otabek.domain.home

import kotlinx.coroutines.flow.Flow

interface SetThemeUseCase {
    operator fun invoke() : Flow<Unit>
}