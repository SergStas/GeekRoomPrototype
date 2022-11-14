package com.example.geekroomprototype.di.modules

import com.example.domain.usecases.GetLoggedInUserUseCase
import com.example.domain.usecases.login.IGetLoggedInUserUseCase
import com.example.domain.usecases.login.ILoginUseCase
import com.example.domain.usecases.login.LoginUseCase
import com.example.domain.usecases.register.IRegistrationUseCase
import com.example.domain.usecases.register.RegistrationUseCase
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {
    @Binds
    fun bindRegisterUseCase(useCase: RegistrationUseCase): IRegistrationUseCase

    @Binds
    fun bindLoginUseCase(useCase: LoginUseCase): ILoginUseCase

    @Binds
    fun bindGetLoggedInUserUseCase(useCase: GetLoggedInUserUseCase): IGetLoggedInUserUseCase
}