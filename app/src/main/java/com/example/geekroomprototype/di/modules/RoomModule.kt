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
}