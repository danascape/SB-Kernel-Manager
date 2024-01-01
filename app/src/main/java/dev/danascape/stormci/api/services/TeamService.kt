package dev.danascape.stormci.api.services

import dev.danascape.stormci.api.ApiEndPoints
import dev.danascape.stormci.models.TeamList
import retrofit2.Response
import retrofit2.http.GET

interface TeamService {
    @GET(ApiEndPoints.teamInfo)
    suspend fun fetchTeam(): Response<TeamList>
}