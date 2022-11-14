package com.example.domain.usecases.login

import com.example.domain.models.UserData

interface IGetLoggedInUserUseCase {
    suspend operator fun invoke(): UserData?
}