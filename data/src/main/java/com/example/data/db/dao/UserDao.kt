package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.db.entities.UserEntity

@Dao
interface UserDao {
    @Query("select * from users")
    suspend fun getAll(): List<UserEntity>

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Insert
    suspend fun create(entity: UserEntity): Long

    @Query("select * from users where username = :username")
    suspend fun getByName(username: String): List<UserEntity>

    @Query("select * from users where id = :id")
    suspend fun getById(id: Long): List<UserEntity>
}