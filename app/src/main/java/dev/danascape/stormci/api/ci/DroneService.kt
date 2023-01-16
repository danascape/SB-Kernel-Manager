package dev.danascape.stormci.api.ci

import dev.danascape.stormci.models.build.BuildHistory
import retrofit2.Response
import retrofit2.http.GET

interface DroneService {
    @GET("builds")
    suspend fun fetchBuildHistory(): Response<List<BuildHistory>>
}