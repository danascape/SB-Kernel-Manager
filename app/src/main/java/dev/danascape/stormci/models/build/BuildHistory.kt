package dev.danascape.stormci.models.build

data class BuildHistory(
    val id: Int,
    val number: Int,
    val status: String,
    val author_name: String,
    val started: Int,
    val finished: Int,
    val params: Params?
)