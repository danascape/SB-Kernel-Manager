package dev.danascape.stormci.api

import dev.danascape.stormci.model.ci.BuildHistory
import dev.danascape.stormci.model.ci.BuildHistoryList
import dev.danascape.stormci.model.device.DevicesList
import retrofit2.Call
import retrofit2.http.GET

interface AutomationService {
    @GET("builds")
    fun fetchBuildHistory(): Call<BuildHistoryList>
}