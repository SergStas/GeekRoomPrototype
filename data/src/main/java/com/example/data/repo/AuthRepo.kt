package com.example.data.repo

import android.content.SharedPreferences
import com.example.data.db.dao.UserDao
import com.example.data.db.entities.UserEntity.Companion.toDbEntity
import com.example.domain.models.AuthData
import com.example.domain.models.UserData
import com.example.domain.repo.IAuthRepo
import com.example.domain.usecases.login.models.LoginResult
import com.example.domain.usecases.register.models.RegistrationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val userDao: UserDao,
): IAuthRepo {
    companion object {
        private const val USER_NAME_KEY = "userName"
        private const val USER_AVATAR_KEY = "userAvatar"
    }

    override suspend fun register(registrationData: AuthData) =
        withContext(Dispatchers.IO) {
            try {
                val names = userDao.getAll().map { it.username }
                if (registrationData.username in names) {
                    RegistrationResult.Error("Username is occupied")
                } else if (registrationData.username.length < 4) {
                    RegistrationResult.Error("Username must be at least 4 characters")
                } else if (registrationData.password.length < 8) {
                    RegistrationResult.Error("Password must be at least 8 characters")
                } else {
                    userDao.create(registrationData.toDbEntity())
                    setLoggedInUser(registrationData.toDbEntity().toUserData())
                    RegistrationResult.Success
                }
            } catch (e: Exception) {
                e.printStackTrace()
                RegistrationResult.Error("Unable to register new user")
            }
        }

    override suspend fun login(data: AuthData) =
        withContext(Dispatchers.IO) {
            try {
                val user = userDao.getByName(data.username)
                    .firstOrNull()
                if (user == null) {
                    LoginResult.Error("Username ${data.username} not found")
                } else if (user.password != data.password) {
                    LoginResult.Error("Invalid password")
                } else {
                    setLoggedInUser(user.toUserData())
                    LoginResult.Success
                }
            } catch (e: Exception) {
                e.printStackTrace()
                LoginResult.Error("Unable to login")
            }
        }

    override suspend fun getUser(): UserData? {
        return UserData(
            username = sharedPreferences.getString(USER_NAME_KEY, null) ?: return null,
            avatarUrl = sharedPreferences.getString(USER_AVATAR_KEY, null) ?: return null,
        )
    }

    override suspend fun logout() = setLoggedInUser(null)

    private fun setLoggedInUser(userData: UserData?) {
        sharedPreferences.edit()
            .putString(USER_NAME_KEY, userData?.username)
            .putString(USER_AVATAR_KEY, userData?.avatarUrl)
            .apply()
    }
}