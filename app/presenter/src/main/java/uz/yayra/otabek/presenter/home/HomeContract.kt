package uz.yayra.otabek.presenter.home

import org.orbitmvi.orbit.ContainerHost
import uz.yayra.otabek.common.TodoItem

/**
Developed by Otabek Nortojiyev
 **/

interface HomeContract {
    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val isLoading: Boolean = false,
        var todos: List<TodoItem> = emptyList(),
        var done: Int = 0,
        var eye: Boolean = true
    )

    sealed interface SideEffect {

    }

    interface Direction {
        suspend fun openAddScreen(data: TodoItem? = null)
    }

    interface Intent {
        data object Init : Intent
        data class OpenAddScreen(val data: TodoItem? = null) : Intent
        data class DeleteTask(val data: TodoItem) : Intent
        data object GetAll : Intent
        data object GetActive : Intent
        data class Update(val data: TodoItem) : Intent
        data object ChangeEye : Intent
    }
}