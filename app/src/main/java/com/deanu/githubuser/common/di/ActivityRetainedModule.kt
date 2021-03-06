package com.deanu.githubuser.common.di

import com.deanu.githubuser.common.data.GitHubUserRepository
import com.deanu.githubuser.common.domain.repository.UserRepository
import com.deanu.githubuser.common.utils.CoroutineDispatchersProvider
import com.deanu.githubuser.common.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindGithubUserRepository(repository: GitHubUserRepository): UserRepository

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider): DispatchersProvider
}