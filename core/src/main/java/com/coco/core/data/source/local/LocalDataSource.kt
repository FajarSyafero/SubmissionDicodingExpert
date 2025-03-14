package com.coco.core.data.source.local

import com.coco.core.data.source.local.entity.FavoriteEntity
import com.coco.core.data.source.local.room.FavoriteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val favoriteDao: FavoriteDao) {
    suspend fun insertFavoriteToDb(favoriteEntity: FavoriteEntity) = favoriteDao.insertFavoriteToDb(favoriteEntity)
    fun getAllFavorite(): Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorite()
    suspend fun deleteFavoriteFromDb(favoriteEntity: FavoriteEntity) = favoriteDao.deleteFavoriteFromDb(favoriteEntity)
    fun isFavorite(id:Int): Flow<Boolean> = favoriteDao.isFavorite(id)
}