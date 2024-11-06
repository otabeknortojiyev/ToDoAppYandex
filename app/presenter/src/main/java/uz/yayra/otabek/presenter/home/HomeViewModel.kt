package uz.yayra.otabek.presenter.home

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
import uz.yayra.otabek.domain.useCase.home.ChangeEyeUseCase
import uz.yayra.otabek.domain.useCase.home.CheckEyeUseCase
import uz.yayra.otabek.domain.useCase.home.DeleteUseCase
import uz.yayra.otabek.domain.useCase.home.GetCompleteCountUseCase
import uz.yayra.otabek.domain.useCase.home.GetTodoUseCase
import uz.yayra.otabek.domain.useCase.home.UpdateUseCase
import javax.inject.Inject

/**
Developed by Otabek Nortojiyev
 **/


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val direction: HomeContract.Direction,
    private val getTodoUseCase: GetTodoUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val updateUseCase: UpdateUseCase,
    private val checkEyeUseCase: CheckEyeUseCase,
    private val changeEyeUseCase: ChangeEyeUseCase,
    private val getCompleteCountUseCase: GetCompleteCountUseCase
) : ViewModel(), HomeContract.ViewModel {
    override fun onEventDispatcher(intent: HomeContract.Intent) = intent() {
        when (intent) {
            HomeContract.Intent.Init -> {
                checkEyeUseCase.invoke().onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    getCompleteCountUseCase.invoke().onEach { reduce { state.copy(done = it) } }.launchIn(viewModelScope)
                    getTodoUseCase.invoke(it).onStart {
                        reduce { state.copy(isLoading = true) }
                    }.onEach {
                        reduce { state.copy(todos = it) }
                    }.onCompletion {
                        reduce { state.copy(isLoading = false) }
                    }.launchIn(viewModelScope)
                    reduce { state.copy(eye = it) }
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.OpenAddScreen -> {
                viewModelScope.launch {
                    if (intent.data != null) {
                        direction.openAddScreen(intent.data)
                    } else {
                        direction.openAddScreen()
                    }
                }
            }

            is HomeContract.Intent.DeleteTask -> {
                deleteUseCase.invoke(intent.data).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    updateList()
                }.onCompletion {}.launchIn(viewModelScope)
            }

            HomeContract.Intent.GetAll -> {
                getCompleteCountUseCase.invoke().onEach {
                    reduce { state.copy(done = it) }
                }.launchIn(viewModelScope)
                getTodoUseCase.invoke(isShow = true).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    reduce { state.copy(todos = it) }
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }

            HomeContract.Intent.GetActive -> {
                getCompleteCountUseCase.invoke().onEach {
                    reduce { state.copy(done = it) }
                }.launchIn(viewModelScope)
                getTodoUseCase(isShow = false).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    reduce { state.copy(todos = it) }
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.Update -> {
                updateUseCase.invoke(intent.data).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    updateList()
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }

            HomeContract.Intent.ChangeEye -> {
                changeEyeUseCase.invoke().onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    val eye = state.eye
                    reduce { state.copy(eye = !eye, isLoading = false) }
                }.onCompletion {}.launchIn(viewModelScope)
            }
        }
    }

    private fun updateList() = intent {
        checkEyeUseCase.invoke().onStart {
            reduce { state.copy(isLoading = true) }
        }.onEach {
            getCompleteCountUseCase.invoke().onEach { reduce { state.copy(done = it) } }.launchIn(viewModelScope)
            getTodoUseCase.invoke(it).onStart {
                reduce { state.copy(isLoading = true) }
            }.onEach {
                reduce { state.copy(todos = it) }
            }.onCompletion {
                reduce { state.copy(isLoading = false) }
            }.launchIn(viewModelScope)
            reduce { state.copy(eye = it) }
        }.onCompletion {
            reduce {
                state.copy(isLoading = false)
            }
        }.launchIn(viewModelScope)
    }

    override val container = container<HomeContract.UiState, HomeContract.SideEffect>(HomeContract.UiState())
}