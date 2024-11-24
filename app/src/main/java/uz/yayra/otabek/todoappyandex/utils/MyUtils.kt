package uz.yayra.otabek.todoappyandex.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatLongToDateString(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
    return format.format(date)
}