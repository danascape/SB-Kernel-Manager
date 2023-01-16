package dev.danascape.stormci.api.team

import dev.danascape.stormci.models.team.TeamList
import retrofit2.Response
import retrofit2.http.GET

interface TeamService {
    @GET("team/team.json")
    suspend fun fetchTeam(): Response<TeamList>
}