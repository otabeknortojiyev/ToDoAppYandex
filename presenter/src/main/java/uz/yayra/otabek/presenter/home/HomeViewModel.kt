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
import uz.yayra.otabek.domain.home.ChangeEyeUseCase
import uz.yayra.otabek.domain.home.CheckEyeUseCase
import uz.yayra.otabek.domain.home.DeleteUseCase
import uz.yayra.otabek.domain.home.GetCompleteCountUseCase
import uz.yayra.otabek.domain.home.GetTodoUseCase
import uz.yayra.otabek.domain.home.SyncUseCase
import uz.yayra.otabek.domain.home.UpdateUseCase
import uz.yayra.otabek.presenter.utils.NetworkStatusValidator
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
    private val getCompleteCountUseCase: GetCompleteCountUseCase,
    private val networkStatusValidator: NetworkStatusValidator,
    private val syncUseCase: SyncUseCase
) : ViewModel(), HomeContract.ViewModel {

    override val container = container<HomeContract.UiState, HomeContract.SideEffect>(HomeContract.UiState(internet = networkStatusValidator.isNetworkEnabled))

    init {
        networkStatusValidator.fl.onEach { internetStatus ->
            intent {
                reduce { state.copy(internet = internetStatus) }
            }
            if (internetStatus) {
                syncUseCase.invoke().onEach {
                }.onCompletion { updateList() }.launchIn(viewModelScope)
            }
        }.launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: HomeContract.Intent) = intent() {
        when (intent) {

            HomeContract.Intent.Init -> {
                updateList()
            }

            HomeContract.Intent.GetAll -> {
                updateList()
            }

            HomeContract.Intent.ChangeEye -> {
                changeEyeUseCase.invoke()
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

            is HomeContract.Intent.Delete -> {
                deleteUseCase.invoke(intent.data, state.internet).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    updateList()
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.Update -> {
                updateUseCase.invoke(intent.data, state.internet).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    updateList()
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun updateList() = intent {
        checkEyeUseCase.invoke().onStart {
            reduce { state.copy(isLoading = true) }
        }.onEach {
            getTodoUseCase.invoke(state.internet, it).onStart {
                reduce { state.copy(isLoading = true) }
            }.onEach {
                reduce { state.copy(todos = it) }
                getCompleteCountUseCase.invoke().onEach { reduce { state.copy(done = it) } }.launchIn(viewModelScope)
            }.onCompletion {
                reduce { state.copy(isLoading = false) }
            }.launchIn(viewModelScope)
            reduce { state.copy(eye = it) }
        }.onCompletion {
            reduce { state.copy(isLoading = false) }
        }.launchIn(viewModelScope)
    }
}