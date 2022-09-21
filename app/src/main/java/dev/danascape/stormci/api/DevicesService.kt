package dev.danascape.stormci.api

import dev.danascape.stormci.model.DevicesList
import retrofit2.Call
import retrofit2.http.GET

interface DevicesService {
    @GET("test.json")
    fun fetchDevices(): Call<DevicesList>
}