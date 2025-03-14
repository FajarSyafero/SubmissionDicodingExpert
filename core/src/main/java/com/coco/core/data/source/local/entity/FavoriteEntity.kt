package com.coco.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_favorite")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "image")
    val img: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,
    @ColumnInfo(name = "vote_count")
    val voteCount: Double?,
    @ColumnInfo(name = "runtime")
    val runtime: Int?,
    @ColumnInfo(name = "release_date")
    val releaseDate: String?,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean?,
)
