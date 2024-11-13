package uz.yayra.otabek.presenter.home

import org.orbitmvi.orbit.ContainerHost
import uz.yayra.otabek.common.TodoEntity

/**
Developed by Otabek Nortojiyev
 **/

interface HomeContract {
    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val isLoading: Boolean = false, var todos: List<TodoEntity> = emptyList(), var done: Int = 0, var eye: Boolean = true, val internet: Boolean
    )

    sealed interface SideEffect {

    }

    interface Direction {
        suspend fun openAddScreen(data: TodoEntity? = null)
    }

    interface Intent {
        data object Init : Intent
        data object GetAll : Intent
        data object ChangeEye : Intent
        data class OpenAddScreen(val data: TodoEntity? = null) : Intent
        data class Update(val data: TodoEntity) : Intent
        data class Delete(val data: TodoEntity) : Intent
    }
}