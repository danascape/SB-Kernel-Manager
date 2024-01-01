package dev.danascape.stormci.api.services

import dev.danascape.stormci.api.ApiEndPoints
import dev.danascape.stormci.models.BuildHistory
import retrofit2.Response
import retrofit2.http.GET

interface DroneService {
    @GET(ApiEndPoints.droneBuildInfo)
    suspend fun fetchBuildHistory(): Response<List<BuildHistory>>
}