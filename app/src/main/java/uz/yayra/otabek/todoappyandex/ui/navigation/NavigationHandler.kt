package uz.yayra.otabek.todoappyandex.ui.navigation

import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.flow.SharedFlow

typealias NavigationArgs = Navigator.() -> Unit

interface NavigationHandler {
    val screenState: SharedFlow<NavigationArgs>
}