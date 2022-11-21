package com.example.domain.repo

import com.example.domain.models.UserData

interface IUsersRepo {
    suspend fun getAllUsers(): List<UserData>
}