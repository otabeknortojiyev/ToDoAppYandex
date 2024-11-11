package uz.yayra.otabek.presenter.activity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.yayra.otabek.domain.useCase.home.GetThemeUseCase
import javax.inject.Inject

/**
Developed by Otabek Nortojiyev
 **/
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val getThemeUseCase: GetThemeUseCase) : ViewModel() {

    fun getTheme(): Boolean = getThemeUseCase()
}