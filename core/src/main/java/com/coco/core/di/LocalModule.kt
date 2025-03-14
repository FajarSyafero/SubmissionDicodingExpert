package com.coco.core.di

import android.content.Context
import androidx.room.Room
import com.coco.core.data.source.local.room.FavoriteDao
import com.coco.core.data.source.local.room.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context:Context): FavoriteDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context, FavoriteDatabase::class.java, "favorite.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }


    @Provides
    fun provideFavoriteDao(favoriteDatabase: FavoriteDatabase): FavoriteDao =
        favoriteDatabase.favoriteDao()
}