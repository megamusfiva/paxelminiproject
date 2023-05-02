package com.example.paxelminiproject.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("owner")
    val owner: OwnerResponse,
    @SerializedName("html_url")
    val htmlUrl: String?,
    @SerializedName("description")
    val description: String?
)

data class OwnerResponse(
    @SerializedName("login")
    var login: String?,
    @SerializedName("avatar_url")
    var avatarUrl: String?
)
