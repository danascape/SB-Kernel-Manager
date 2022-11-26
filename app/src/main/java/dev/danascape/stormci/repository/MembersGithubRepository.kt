package dev.danascape.stormci.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.danascape.stormci.api.team.TeamService
import dev.danascape.stormci.models.team.Team
import javax.inject.Inject

class MembersGithubRepository @Inject constructor(
    val teamService: TeamService,
) {

    private val _teamMembers = MutableLiveData<NetworkResponse<List<Team>>>()
    val teamMembers: LiveData<NetworkResponse<List<Team>>> = _teamMembers

    private val _coreMembers = MutableLiveData<NetworkResponse<List<Team>>>()
    val coreMembers: LiveData<NetworkResponse<List<Team>>> = _coreMembers

    suspend fun getTeamMembers() {
        val response = teamService.fetchMaintainer()
        try {
            if (response.isSuccessful && response.body() != null) {
                val teamList = response.body()?.members
                _teamMembers.postValue(NetworkResponse.Success(teamList))
            }
        } catch (e: Exception) {
            _teamMembers.postValue(NetworkResponse.Error(response.errorBody().toString()))
        }
    }

    suspend fun getCoreMembers() {
        val response = teamService.fetchCoreTeam()

        try {
            if (response.isSuccessful && response.body() != null) {
                val coreList = response.body()?.members
                _coreMembers.postValue(NetworkResponse.Success(coreList))
            }
        } catch (e: Exception) {
            _coreMembers.postValue(NetworkResponse.Error(response.errorBody().toString()))
        }
    }
}