package dev.danascape.stormci.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danascape.stormci.models.device.Device
import dev.danascape.stormci.repository.DevicesGithubRespository
import dev.danascape.stormci.repository.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(private val devicesGithubRespository: DevicesGithubRespository) :
    ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            devicesGithubRespository.getDeviceList()
        }
    }

    val deviceList: LiveData<NetworkResponse<List<Device>>>
        get() = devicesGithubRespository.deviceList
}