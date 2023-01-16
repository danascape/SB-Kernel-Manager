package dev.danascape.stormci.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.danascape.stormci.api.team.TeamService
import dev.danascape.stormci.models.team.Team
import javax.inject.Inject

class MembersGithubRepository @Inject constructor(
    val teamService: TeamService,
) {

    private val _Members = MutableLiveData<NetworkResponse<List<Team>>>()
    val Members: LiveData<NetworkResponse<List<Team>>> = _Members

    suspend fun getMembers() {
        val response = teamService.fetchTeam()

        try {
            if (response.isSuccessful && response.body() != null) {
                val List = response.body()?.members
                _Members.postValue(NetworkResponse.Success(List))
            }
        } catch (e: Exception) {
            _Members.postValue(NetworkResponse.Error(response.errorBody().toString()))
        }
    }
}