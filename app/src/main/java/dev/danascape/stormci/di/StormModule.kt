package dev.danascape.stormci.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.danascape.stormci.api.services.DeviceService
import dev.danascape.stormci.api.services.TeamService
import dev.danascape.stormci.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StormModule {

    @Provides
    @Singleton
    @GithubApi
    fun provideGithubRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideDeviceService(@GithubApi retrofit: Retrofit): DeviceService =
        retrofit.create(DeviceService::class.java)

    @Provides
    fun provideTeamService(@GithubApi retrofit: Retrofit): TeamService =
        retrofit.create(TeamService::class.java)
}