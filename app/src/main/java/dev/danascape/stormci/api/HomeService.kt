package dev.danascape.stormci.api

import dev.danascape.stormci.model.BuildModel
import retrofit2.Call
import retrofit2.http.GET

interface HomeService {
    @GET("rom.json")
    fun getBuildInfo(): Call<BuildModel>
}