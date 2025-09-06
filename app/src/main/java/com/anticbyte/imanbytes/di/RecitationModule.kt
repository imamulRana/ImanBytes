package com.anticbyte.imanbytes.di

import com.anticbyte.imanbytes.data.repo.RecitationRepoImpl
import com.anticbyte.imanbytes.domain.repo.RecitationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecitationModule {
    @Provides
    @Singleton
    fun provideRecitationRepo(httpClient: HttpClient): RecitationRepo =
        RecitationRepoImpl(httpClient)
}