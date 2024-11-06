package uz.yayra.otabek.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.yayra.otabek.data.local.room.TodoDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Provides Singleton]
    fun provideCardDatabase(@ApplicationContext context: Context): TodoDataBase =
        Room.databaseBuilder(context = context, TodoDataBase::class.java, "ToDoAppYandex.db")
            .build()

    @[Provides Singleton]
    fun provideTodoDao(database: TodoDataBase) = database.todoDao()
}