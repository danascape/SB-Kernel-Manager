package dev.danascape.stormci.models

import com.google.gson.annotations.SerializedName

data class Params(
    @SerializedName("DEVICE")
    val device: String?,

    @SerializedName("BRANCH")
    val branch: String?
)