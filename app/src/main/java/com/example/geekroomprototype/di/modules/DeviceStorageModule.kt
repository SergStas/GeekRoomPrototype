package com.example.geekroomprototype.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class DeviceStorageModule {
    companion object {
        private const val DB_TAG = "db"
    }

    @Provides
    fun provideSharedPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(DB_TAG, Context.MODE_PRIVATE)
}