package dev.danascape.stormci.api.team

import dev.danascape.stormci.models.team.TeamList
import retrofit2.Response
import retrofit2.http.GET

interface TeamService {
    @GET("team/core.json")
    suspend fun fetchCoreTeam(): Response<TeamList>

    @GET("team/maintainers.json")
    suspend fun fetchMaintainer(): Response<TeamList>

}