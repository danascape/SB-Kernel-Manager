package dev.danascape.stormci.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.danascape.stormci.api.services.DeviceService
import dev.danascape.stormci.models.Device
import javax.inject.Inject

class DevicesGithubRespository @Inject constructor(private val deviceService: DeviceService) {

    private val _deviceList = MutableLiveData<NetworkResponse<List<Device>>>()
    val deviceList: LiveData<NetworkResponse<List<Device>>> = _deviceList

    suspend fun getDeviceList() {
        val response = deviceService.fetchDevices()
        try {
            if (response.isSuccessful && response.body()!=null) {
                val listDevice = response.body()!!.devices ?: emptyList()
                _deviceList.postValue(NetworkResponse.Success(listDevice))
            }
        } catch (e: Exception) {
            _deviceList.postValue(NetworkResponse.Error(response.message().toString()))
        }
    }
}