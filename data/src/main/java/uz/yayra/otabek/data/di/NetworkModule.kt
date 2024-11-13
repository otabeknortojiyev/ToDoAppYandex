package uz.yayra.otabek.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.yayra.otabek.data.local.LocalStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @[Provides Singleton]
    fun providesOkHttpClient(
        @ApplicationContext context: Context,
        storage: LocalStorage,
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(ChuckerInterceptor.Builder(context).build()).addInterceptor { chain ->
        val request = chain.request()
        val newRequest = request.newBuilder().header("Authorization", "Bearer ${storage.token}").header("X-Last-Known-Revision", "${storage.revision.toInt()}").build()
        chain.proceed(newRequest)
    }.build()

    @[Provides Singleton]
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl("https://hive.mrdekk.ru/todo/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
}