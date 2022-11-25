package dev.danascape.stormci.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.danascape.stormci.api.ci.DroneService
import dev.danascape.stormci.api.device.DeviceService
import dev.danascape.stormci.api.team.TeamService
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
    @Singleton
    @DroneApi
    fun provideDroneRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.DRONE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideDroneService(@DroneApi retrofit: Retrofit): DroneService =
        retrofit.create(DroneService::class.java)

    @Provides
    fun provideDeviceService(@GithubApi retrofit: Retrofit): DeviceService =
        retrofit.create(DeviceService::class.java)

    @Provides
    fun provideTeamService(@GithubApi retrofit: Retrofit): TeamService =
        retrofit.create(TeamService::class.java)
}