package com.example.geekroomprototype

import android.app.Application
import androidx.fragment.app.Fragment
import com.example.geekroomprototype.di.AppComponent
import com.example.geekroomprototype.di.DaggerAppComponent
import com.example.geekroomprototype.di.modules.AppModule

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}