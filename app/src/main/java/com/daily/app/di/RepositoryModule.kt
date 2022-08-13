package com.daily.app.di

import com.daily.app.data.remote.AppAPI
import com.daily.app.data.repositories.AppRepoImp
import com.daily.app.domain.repositories.AppRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppRepo(
        appRepoImp: AppRepoImp
    ): AppRepo

}