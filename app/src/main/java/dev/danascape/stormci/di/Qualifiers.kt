package dev.danascape.stormci.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DroneApi