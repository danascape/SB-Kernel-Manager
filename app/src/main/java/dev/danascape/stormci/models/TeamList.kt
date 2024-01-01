package dev.danascape.stormci.models

import dev.danascape.stormci.models.Team

data class TeamList(
    val members: List<Team>? = null
)