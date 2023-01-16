package dev.danascape.stormci.models.build

import com.google.gson.annotations.SerializedName

data class Params(
    @SerializedName("DEVICE")
    val device: String?,

    @SerializedName("BRANCH")
    val branch: String?
)