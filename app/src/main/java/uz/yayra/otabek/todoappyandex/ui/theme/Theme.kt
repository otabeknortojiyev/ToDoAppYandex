package uz.yayra.otabek.todoappyandex.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = SupportLightSeparator,
    onPrimary = SupportLightOverlay,
    primaryContainer = LabelLightPrimary,
    onPrimaryContainer = LabelLightSecondary,
    inversePrimary = LabelLightTertiary,
    secondary = LabelLightDisable,
    onSecondary = ColorLightRed,
    secondaryContainer = ColorLightGreen,
    onSecondaryContainer = ColorLightBlue,
    tertiary = ColorLightGray,
    onTertiary = ColorLightGrayLight,
    tertiaryContainer = ColorLightWhite,
    onTertiaryContainer = BackLightPrimary,
    background = BackLightSecondary,
    onBackground = BackLightElevated,
    surface = BackLightRed,
    onSurface = BackLightSwitch
)

private val DarkColorScheme = darkColorScheme(
    primary = SupportDarkSeparator,
    onPrimary = SupportDarkOverlay,
    primaryContainer = LabelDarkPrimary,
    onPrimaryContainer = LabelDarkSecondary,
    inversePrimary = LabelDarkTertiary,
    secondary = LabelDarkDisable,
    onSecondary = ColorDarkRed,
    secondaryContainer = ColorDarkGreen,
    onSecondaryContainer = ColorDarkBlue,
    tertiary = ColorDarkGrey,
    onTertiary = ColorDarkGreyLight,
    tertiaryContainer = ColorDarkWhite,
    onTertiaryContainer = BackDarkPrimary,
    background = BackDarkSecondary,
    onBackground = BackDarkElevated,
    surface = BackDarkRed,
    onSurface = BackDarkSwitch
)

@Composable
fun ToDoAppYandexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}