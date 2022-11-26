package dev.danascape.stormci.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.danascape.stormci.api.ci.DroneService
import dev.danascape.stormci.models.ci.BuildHistory
import javax.inject.Inject

class DroneRepository @Inject constructor(val droneService: DroneService) {
    private var _buildHistory = MutableLiveData<NetworkResponse<List<BuildHistory>>>()
    val buildHistory: LiveData<NetworkResponse<List<BuildHistory>>> = _buildHistory

    suspend fun provideBuildHistory() {
        val response = droneService.fetchBuildHistory()
        try {
            if (response.isSuccessful) {
                _buildHistory.postValue(NetworkResponse.Success(response.body()))
            }
        } catch (e: Exception) {
            _buildHistory.postValue(NetworkResponse.Error(response.errorBody().toString()))
        }
    }
}