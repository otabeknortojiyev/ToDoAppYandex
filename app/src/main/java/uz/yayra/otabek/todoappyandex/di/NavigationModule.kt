package uz.yayra.otabek.todoappyandex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.yayra.otabek.todoappyandex.ui.navigation.AppNavigationDispatcher
import uz.yayra.otabek.todoappyandex.ui.navigation.AppNavigator
import uz.yayra.otabek.todoappyandex.ui.navigation.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    fun bindAppNavigator(dispatcher: AppNavigationDispatcher): AppNavigator

    @Binds
    fun bindNavigationHandler(dispatcher: AppNavigationDispatcher): NavigationHandler
}