package dev.danascape.stormci.api.services

import dev.danascape.stormci.api.ApiEndPoints
import dev.danascape.stormci.models.DeviceList
import retrofit2.Response
import retrofit2.http.GET

interface DeviceService {
    @GET(ApiEndPoints.deviceInfo)
    suspend fun fetchDevices(): Response<DeviceList>
}