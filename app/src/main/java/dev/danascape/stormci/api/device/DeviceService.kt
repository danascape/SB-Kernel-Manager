package dev.danascape.stormci.api.device

import dev.danascape.stormci.models.device.DeviceList
import retrofit2.Response
import retrofit2.http.GET

interface DeviceService {
    @GET("devices/devices.json")
    suspend fun fetchDevices(): Response<DeviceList>
}