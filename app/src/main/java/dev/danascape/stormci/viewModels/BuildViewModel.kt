package dev.danascape.stormci.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danascape.stormci.models.BuildHistory
import dev.danascape.stormci.repository.DroneRepository
import dev.danascape.stormci.repository.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuildViewModel @Inject constructor(private val droneRepository: DroneRepository) :
    ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            droneRepository.provideBuildHistory()
        }
    }

    val buildHistory: LiveData<NetworkResponse<List<BuildHistory>>>
        get() = droneRepository.buildHistory
}