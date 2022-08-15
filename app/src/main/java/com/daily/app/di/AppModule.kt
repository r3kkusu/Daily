package com.daily.app.di

import android.R
import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.daily.app.common.Constants
import com.daily.app.data.local.AppDB
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

    @Provides
    @Singleton
    fun provideAppDB(app: Application): AppDB {
        return Room.databaseBuilder(
            app,
            AppDB::class.java,
            Constants.APP_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.mipmap.sym_def_app_icon)
            .error(R.mipmap.sym_def_app_icon)
    }

    @Provides
    @Singleton
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }
}