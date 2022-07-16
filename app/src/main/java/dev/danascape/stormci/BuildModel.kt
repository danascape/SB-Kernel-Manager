package dev.danascape.stormci

import com.google.gson.annotations.SerializedName

data class BuildModel(
    @SerializedName("name")
    val name: String,
    val device: String,
    val branch: String,
    val status: String
)

data class BuildDevice(
    @SerializedName("device")
    val device: String
)

data class BuildBranch(
    @SerializedName("Branch")
    val branch: String
)

data class BuildStatus(
    @SerializedName("Status")
    val status: String
)