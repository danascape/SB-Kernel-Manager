package dev.danascape.stormci.models

data class BuildHistory(
    val id: Int,
    val number: Int,
    val status: String,
    val started: Int,
    val finished: Int,
    val params: Params?
)