package uz.yayra.otabek.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalStorage @Inject constructor(@ApplicationContext context: Context) : SharedPreference(context) {
    var isDark by booleans(false)
    var eye by booleans(true)
}