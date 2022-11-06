package dev.danascape.stormci.model.ci

data class BuildHistoryList(
    val id: Int,
    val number: Int,
    val status: String,
    val author_name: String,
    val started: Int,
    val finished: Int
)