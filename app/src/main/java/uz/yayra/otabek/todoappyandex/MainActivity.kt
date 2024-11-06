package uz.yayra.otabek.todoappyandex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import uz.yayra.otabek.todoappyandex.ui.theme.ToDoAppYandexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppYandexTheme {

            }
        }
    }
}