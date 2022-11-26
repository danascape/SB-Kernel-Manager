package dev.danascape.stormci.ui.fragments.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.danascape.stormci.models.team.Team
import dev.danascape.stormci.repository.MembersGithubRepository
import dev.danascape.stormci.repository.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoreTeamViewModel @Inject constructor(private val membersGithubRepository: MembersGithubRepository) :
    ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            membersGithubRepository.getCoreMembers()
        }
    }

    val coreMember: LiveData<NetworkResponse<List<Team>>>
        get() = membersGithubRepository.coreMembers

}