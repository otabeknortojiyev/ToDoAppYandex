package uz.yayra.otabek.todoappyandex.screens.addTodo

import uz.yayra.otabek.presenter.addTodo.AddTaskContract
import uz.yayra.otabek.todoappyandex.ui.navigation.AppNavigator
import javax.inject.Inject

/**
Developed by Otabek Nortojiyev
 **/

class AddTaskDirection @Inject constructor(private val navigator: AppNavigator) : AddTaskContract.Direction {
    override suspend fun back() {
        navigator.back()
    }
}
