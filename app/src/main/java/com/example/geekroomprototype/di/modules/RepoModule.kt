package com.example.geekroomprototype.di.modules

import com.example.data.repo.ArticlesRepo
import com.example.data.repo.AuthRepo
import com.example.domain.repo.IArticleRepo
import com.example.domain.repo.IAuthRepo
import dagger.Binds
import dagger.Module

@Module
interface RepoModule {
    @Binds
    fun bindAuthRepo(repo: AuthRepo): IAuthRepo

    @Binds
    fun bindArticlesRepo(repo: ArticlesRepo): IArticleRepo
}