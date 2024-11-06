package uz.yayra.otabek.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.yayra.otabek.domain.home.ChangeEyeUseCase
import uz.yayra.otabek.domain.home.CheckEyeUseCase
import uz.yayra.otabek.domain.home.DeleteUseCase
import uz.yayra.otabek.domain.home.GetCompleteCountUseCase
import uz.yayra.otabek.domain.home.GetThemeUseCase
import uz.yayra.otabek.domain.home.GetTodoActiveUseCase
import uz.yayra.otabek.domain.home.GetTodoUseCase
import uz.yayra.otabek.domain.home.InsertUseCase
import uz.yayra.otabek.domain.home.SetThemeUseCase
import uz.yayra.otabek.domain.home.UpdateUseCase
import uz.yayra.otabek.domain.home.impl.ChangeEyeUseCaseImpl
import uz.yayra.otabek.domain.home.impl.CheckEyeUseCaseImpl
import uz.yayra.otabek.domain.home.impl.DeleteUseCaseImpl
import uz.yayra.otabek.domain.home.impl.GetCompleteCountUseCaseImpl
import uz.yayra.otabek.domain.home.impl.GetThemeUseCaseImpl
import uz.yayra.otabek.domain.home.impl.GetTodoActiveUseCaseImpl
import uz.yayra.otabek.domain.home.impl.GetTodoUseCaseImpl
import uz.yayra.otabek.domain.home.impl.InsertUseCaseImpl
import uz.yayra.otabek.domain.home.impl.SetThemeUseCaseImpl
import uz.yayra.otabek.domain.home.impl.UpdateUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface UseCaseModule {

    @[Binds ViewModelScoped]
    fun provideGetTodoUC(useCaseImpl: GetTodoUseCaseImpl): GetTodoUseCase

    @[Binds ViewModelScoped]
    fun provideGetTodoActiveUC(useCaseImpl: GetTodoActiveUseCaseImpl): GetTodoActiveUseCase

    @[Binds ViewModelScoped]
    fun provideInsertUC(useCaseImpl: InsertUseCaseImpl): InsertUseCase

    @[Binds ViewModelScoped]
    fun provideUpdateUC(useCaseImpl: UpdateUseCaseImpl): UpdateUseCase

    @[Binds ViewModelScoped]
    fun provideDeleteUC(useCaseImpl: DeleteUseCaseImpl): DeleteUseCase

    @[Binds ViewModelScoped]
    fun provideCheckEyeUC(useCaseImpl: CheckEyeUseCaseImpl): CheckEyeUseCase

    @[Binds ViewModelScoped]
    fun provideChangeEyeUC(useCaseImpl: ChangeEyeUseCaseImpl): ChangeEyeUseCase

    @[Binds ViewModelScoped]
    fun provideGetCompleteCountUC(useCaseImpl: GetCompleteCountUseCaseImpl): GetCompleteCountUseCase

    @[Binds ViewModelScoped]
    fun provideGetThemeUC(useCaseImpl: GetThemeUseCaseImpl): GetThemeUseCase

    @[Binds ViewModelScoped]
    fun provideSetThemeUC(useCaseImpl: SetThemeUseCaseImpl): SetThemeUseCase
}