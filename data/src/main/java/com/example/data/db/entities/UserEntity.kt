package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.AuthData
import com.example.domain.models.UserData

@Entity(tableName = "users")
data class UserEntity(
    val username: String,
    val password: String,
    val avatarUrl: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
) {
    companion object {
        fun AuthData.toDbEntity() =
            UserEntity(
                username = username,
                password = password,
                avatarUrl = "",
            )
    }

    fun toAuthData() = AuthData(username, password)
    fun toUserData() = UserData(username, avatarUrl)
}
