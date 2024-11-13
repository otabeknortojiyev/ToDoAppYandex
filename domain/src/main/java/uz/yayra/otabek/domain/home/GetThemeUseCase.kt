package uz.yayra.otabek.domain.home

import kotlinx.coroutines.flow.Flow

interface GetThemeUseCase {
    operator fun invoke(): Boolean
}