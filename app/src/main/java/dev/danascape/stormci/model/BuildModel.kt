package dev.danascape.stormci.model

import com.google.gson.annotations.SerializedName

data class BuildModel(
    @SerializedName("name")
    val name: String,
    val device: String,
    val branch: String,
    val status: String
)