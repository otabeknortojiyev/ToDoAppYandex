package uz.yayra.otabek.todoappyandex.screens.home

import uz.yayra.otabek.common.TodoItem
import uz.yayra.otabek.presenter.home.HomeContract
import uz.yayra.otabek.todoappyandex.screens.addTodo.AddTaskScreen
import uz.yayra.otabek.todoappyandex.ui.navigation.AppNavigator
import javax.inject.Inject

/**
Developed by Otabek Nortojiyev
 **/

class HomeDirection @Inject constructor(private val navigator: AppNavigator) : HomeContract.Direction {
    override suspend fun openAddScreen(data: TodoItem?) {
        if (data != null) {
            navigator.push(AddTaskScreen(data))
        } else {
            navigator.push(AddTaskScreen())
        }
    }
}
