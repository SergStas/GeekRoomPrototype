package com.example.data.repo

import com.example.data.db.dao.UserDao
import com.example.data.db.mapping.EntityMapper
import com.example.domain.repo.IUsersRepo
import javax.inject.Inject

class UsersRepo @Inject constructor(
    private val userDao: UserDao,
    private val mapper: EntityMapper,
): IUsersRepo {
    override suspend fun getAllUsers() =
        userDao.getAll().map { mapper.mapUserEntity(it) }
}