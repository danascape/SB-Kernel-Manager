package dev.danascape.stormci.api.ci

import dev.danascape.stormci.models.ci.BuildHistory
import retrofit2.Call
import retrofit2.http.GET

interface DroneService {
    @GET("builds")
    fun fetchBuildHistory(): Call<List<BuildHistory>>
}