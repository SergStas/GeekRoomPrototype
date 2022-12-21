package com.example.geekroomprototype.di.modules

import com.example.data.repo.*
import com.example.domain.repo.*
import dagger.Binds
import dagger.Module

@Module
interface RepoModule {
    @Binds
    fun bindAuthRepo(repo: AuthRepo): IAuthRepo

    @Binds
    fun bindAvatarRepo(repo: AvatarsRepo): IAvatarsRepo

    @Binds
    fun bindArticlesRepo(repo: ArticlesRepo): IArticleRepo

    @Binds
    fun bindChatsRepo(repo: ChatsRepo): IChatsRepo

    @Binds
    fun bindUsersRepo(repo: UsersRepo): IUsersRepo
}