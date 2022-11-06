package dev.danascape.stormci.api.team

import dev.danascape.stormci.model.team.MaintainerList
import retrofit2.Call
import retrofit2.http.GET

interface MaintainerService {
    @GET("team/maintainers.json")
    fun fetchMaintainer(): Call<MaintainerList>
}