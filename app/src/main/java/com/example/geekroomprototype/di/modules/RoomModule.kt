package com.example.geekroomprototype.di.modules

import android.content.Context
import androidx.room.Room
import com.example.data.db.GeekRoomDb
import dagger.Module
import dagger.Provides

@Module
class RoomModule {
    companion object {
        private const val DB_NAME = "GeekRoomDb"
    }

    @Provides
    fun provideDb(context: Context) =
        Room.databaseBuilder(context, GeekRoomDb::class.java, DB_NAME)
            .fallbackToDestructiveMigration().build()

    @Provides
    fun provideUserDao(db: GeekRoomDb) = db.userDao()

    @Provides
    fun provideArticleDao(db: GeekRoomDb) = db.articleDao()

    @Provides
    fun provideLikesDao(db: GeekRoomDb) = db.likesDao()

    @Provides
    fun provideChatsDao(db: GeekRoomDb) = db.chatsDao()

    @Provides
    fun provideMessagesDao(db: GeekRoomDb) = db.messagesDao()
}