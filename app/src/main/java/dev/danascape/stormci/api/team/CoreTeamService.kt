package dev.danascape.stormci.api.team

import dev.danascape.stormci.model.team.CoreTeamList
import dev.danascape.stormci.model.team.MaintainerList
import retrofit2.Call
import retrofit2.http.GET

interface CoreTeamService {
    @GET("team/core.json")
    fun fetchCoreTeam(): Call<CoreTeamList>

    @GET("team/maintainers.json")
    fun fetchMaintainer(): Call<CoreTeamList>
}