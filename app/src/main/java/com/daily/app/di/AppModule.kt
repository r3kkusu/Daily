package com.daily.app.di

import com.daily.app.common.Constants
import com.daily.app.data.remote.AppAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppAPI(): AppAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build()
            .create(AppAPI::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideAppDB(app: Application): AppDB {
//        return Room.databaseBuilder(
//            app,
//            AppDB::class.java,
//            Constants.APP_DATABASE
//        ).build()
//    }
}