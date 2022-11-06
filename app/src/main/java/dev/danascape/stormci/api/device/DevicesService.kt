package dev.danascape.stormci.api.device

import dev.danascape.stormci.model.device.DevicesList
import retrofit2.Call
import retrofit2.http.GET

interface DevicesService {
    @GET("devices/devices.json")
    fun fetchDevices(): Call<DevicesList>
}