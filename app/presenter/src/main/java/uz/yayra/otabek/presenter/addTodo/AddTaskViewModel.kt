package uz.yayra.otabek.presenter.addTodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.yayra.otabek.domain.useCase.home.DeleteUseCase
import uz.yayra.otabek.domain.useCase.home.InsertUseCase
import uz.yayra.otabek.domain.useCase.home.SetThemeUseCase
import uz.yayra.otabek.domain.useCase.home.UpdateUseCase
import javax.inject.Inject

/**
Developed by Otabek Nortojiyev
 **/

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val direction: AddTaskContract.Direction,
    private val insertUseCase: InsertUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val updateUseCase: UpdateUseCase,
    private val setThemeUseCase: SetThemeUseCase
) : ViewModel(), AddTaskContract.ViewModel {
    override fun onEventDispatcher(intent: AddTaskContract.Intent) = intent() {
        when (intent) {
            AddTaskContract.Intent.Back -> {
                viewModelScope.launch {
                    direction.back()
                }
            }

            is AddTaskContract.Intent.Date -> {
                reduce { state.copy(date = intent.date) }
            }

            is AddTaskContract.Intent.Save -> {
                insertUseCase.invoke(intent.data).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    direction.back()
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }

            is AddTaskContract.Intent.Init -> {
                reduce { state.copy(isLoading = true) }
                reduce { state.copy(date = intent.date) }
                reduce { state.copy(isLoading = false) }
            }

            is AddTaskContract.Intent.Delete -> {
                deleteUseCase.invoke(intent.data).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    direction.back()
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }

            is AddTaskContract.Intent.Update -> {
                updateUseCase.invoke(intent.data).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    direction.back()
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }

            AddTaskContract.Intent.SetTheme -> {
                setThemeUseCase()
            }
        }
    }

    override val container = container<AddTaskContract.UiState, AddTaskContract.SideEffect>(AddTaskContract.UiState())
}