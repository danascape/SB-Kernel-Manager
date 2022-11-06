package dev.danascape.stormci.api.team

import dev.danascape.stormci.model.team.CoreTeamList
import retrofit2.Call
import retrofit2.http.GET

interface CoreTeamService {
    @GET("team/core.json")
    fun fetchCoreTeam(): Call<CoreTeamList>
}