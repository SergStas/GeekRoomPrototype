package com.example.geekroomprototype.di.modules

import android.content.Context
import com.example.geekroomprototype.App
import dagger.Module
import dagger.Provides

@Module(includes = [
    DeviceStorageModule::class,
    RoomModule::class,
    ViewModelModule::class,
    RepoModule::class,
])
class AppModule(private val app: App) {
    @Provides
    fun provideContext() = app as Context
}