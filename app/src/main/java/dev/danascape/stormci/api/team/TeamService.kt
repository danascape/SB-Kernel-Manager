package dev.danascape.stormci.api.team

import dev.danascape.stormci.models.team.TeamList
import retrofit2.Call
import retrofit2.http.GET

interface TeamService {
    @GET("team/core.json")
    fun fetchCoreTeam(): Call<TeamList>

    @GET("team/maintainers.json")
    fun fetchMaintainer(): Call<TeamList>
}