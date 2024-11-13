package uz.yayra.otabek.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.yayra.otabek.data.network.HomeApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @[Provides Singleton]
    fun providesHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)
}