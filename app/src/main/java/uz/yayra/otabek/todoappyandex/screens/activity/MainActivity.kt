package uz.yayra.otabek.todoappyandex.screens.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.yayra.otabek.presenter.activity.MainActivityViewModel
import uz.yayra.otabek.todoappyandex.screens.home.HomeScreen
import uz.yayra.otabek.todoappyandex.ui.navigation.NavigationHandler
import uz.yayra.otabek.todoappyandex.ui.theme.ToDoAppYandexTheme
import javax.inject.Inject

val darkTheme = mutableStateOf(false)


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHandler: NavigationHandler
    val viewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            darkTheme.value = viewModel.getTheme()
            ToDoAppYandexTheme(darkTheme = darkTheme.value) {
                Navigator(screen = HomeScreen, onBackPressed = {
                    true
                }) { navigator ->
                    LaunchedEffect(key1 = navigator) {
                        navigationHandler.screenState.onEach {
                            it.invoke(navigator)
                        }.launchIn(lifecycleScope)
                    }
                    SlideTransition(navigator = navigator)
                }
            }
        }
    }
}