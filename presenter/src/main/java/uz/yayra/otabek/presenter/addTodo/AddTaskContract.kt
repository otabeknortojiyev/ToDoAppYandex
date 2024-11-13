package uz.yayra.otabek.presenter.addTodo

import org.orbitmvi.orbit.ContainerHost
import uz.yayra.otabek.common.TodoEntity

/**
Developed by Otabek Nortojiyev
 **/

interface AddTaskContract {
    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val isLoading: Boolean = false,
        val date: Long = 0L,
        val internet: Boolean
    )

    sealed interface SideEffect {

    }

    interface Direction {
        suspend fun back()
    }

    interface Intent {
        data class Init(val date: Long) : Intent
        data class Date(val date: Long) : Intent
        data class Save(val data: TodoEntity) : Intent
        data class Update(val data: TodoEntity) : Intent
        data class Delete(val data: TodoEntity) : Intent
        data object SetTheme : Intent
        data object Back : Intent
    }
}