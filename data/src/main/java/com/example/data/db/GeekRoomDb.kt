package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.*
import com.example.data.db.entities.*

@Database(entities = [
    UserEntity::class,
    ArticleEntity::class,
    UserArticleLikeEntity::class,
    ChatEntity::class,
    MessageEntity::class,
    MessageReadEntity::class,
    ParticipantEntity::class,
], version = 5)
abstract class GeekRoomDb: RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun articleDao(): ArticleDao

    abstract fun likesDao(): LikesDao

    abstract fun chatsDao(): ChatsDao

    abstract fun messagesDao(): MessagesDao
}