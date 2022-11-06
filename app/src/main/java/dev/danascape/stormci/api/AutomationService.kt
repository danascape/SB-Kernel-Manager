package dev.danascape.stormci.api

import dev.danascape.stormci.model.ci.BuildHistoryList
import retrofit2.Call
import retrofit2.http.GET

interface AutomationService {
    @GET("builds")
    fun fetchBuildHistory(): Call<List<BuildHistoryList>>
}