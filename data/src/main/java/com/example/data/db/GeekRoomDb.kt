package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.ArticleDao
import com.example.data.db.dao.LikesDao
import com.example.data.db.dao.UserDao
import com.example.data.db.entities.ArticleEntity
import com.example.data.db.entities.UserArticleLikeEntity
import com.example.data.db.entities.UserEntity

@Database(entities = [
    UserEntity::class,
    ArticleEntity::class,
    UserArticleLikeEntity::class,
], version = 4)
abstract class GeekRoomDb: RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun articleDao(): ArticleDao

    abstract fun likesDao(): LikesDao
}