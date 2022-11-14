package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.db.entities.UserEntity

@Dao
interface UserDao {
    @Query("select * from users")
    suspend fun getAll(): List<UserEntity>

    @Insert
    suspend fun save(entity: UserEntity)

    @Query("select * from users where username = :username")
    suspend fun getByName(username: String): List<UserEntity>
}