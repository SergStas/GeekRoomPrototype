package com.example.geekroomprototype.di

import com.example.geekroomprototype.di.modules.AppModule
import com.example.geekroomprototype.di.modules.ViewModelModule
import dagger.Component

@Component(modules = [
    AppModule::class,
])
interface AppComponent {
    fun getViewModelFactory(): ViewModelModule.ViewModelFactory
}