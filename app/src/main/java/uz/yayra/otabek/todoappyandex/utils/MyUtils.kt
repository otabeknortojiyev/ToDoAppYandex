package uz.yayra.otabek.todoappyandex.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatLongToDateString(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
    return format.format(date)
}

fun getScreenSizeInDp(context: Context): Float {
    val displayMetrics = context.resources.displayMetrics
    val heightDp = displayMetrics.heightPixels / displayMetrics.density
    return heightDp
}