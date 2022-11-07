package dev.danascape.stormci.model.ci

import com.google.gson.annotations.SerializedName

data class BuildHistoryList(
    val id: Int,
    val number: Int,
    val status: String,
    val author_name: String,
    val started: Int,
    val finished: Int,
    val params: Params?
)

data class Params(
    @SerializedName("DEVICE")
    val device: String?,

    @SerializedName("BRANCH")
    val branch: String?
)