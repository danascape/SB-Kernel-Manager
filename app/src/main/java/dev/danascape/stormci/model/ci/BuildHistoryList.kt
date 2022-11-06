package dev.danascape.stormci.model.ci

import com.google.gson.annotations.SerializedName

data class BuildHistoryList(
    val id: Int,
    val status: String
)