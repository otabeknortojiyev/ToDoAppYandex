package uz.yayra.otabek.todoappyandex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.yayra.otabek.presenter.addTodo.AddTaskContract
import uz.yayra.otabek.presenter.home.HomeContract
import uz.yayra.otabek.todoappyandex.screens.addTodo.AddTaskDirection
import uz.yayra.otabek.todoappyandex.screens.home.HomeDirection

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds ViewModelScoped]
    fun bindHomeDirection(impl: HomeDirection): HomeContract.Direction

    @[Binds ViewModelScoped]
    fun bindAddTaskDirection(impl: AddTaskDirection): AddTaskContract.Direction
}