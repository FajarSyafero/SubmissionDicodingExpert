package com.coco.core.di

import com.coco.core.data.source.repository.MovieRepository
import com.coco.core.domain.repository.IMovieRepository
import com.coco.core.di.NetworkModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMovieRepository(movieRepository: MovieRepository): IMovieRepository
}