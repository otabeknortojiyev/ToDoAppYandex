package uz.yayra.otabek.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.yayra.otabek.data.repository.TodoItemRepository
import uz.yayra.otabek.data.repository.impl.TodoItemRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun provideHomeRepository(impl: TodoItemRepositoryImpl): TodoItemRepository
}