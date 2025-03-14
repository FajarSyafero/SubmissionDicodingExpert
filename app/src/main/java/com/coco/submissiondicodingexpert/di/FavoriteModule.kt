package com.coco.submissiondicodingexpert.di

import com.coco.core.domain.usecase.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModule {
    fun movieUseCase(): MovieUseCase
}