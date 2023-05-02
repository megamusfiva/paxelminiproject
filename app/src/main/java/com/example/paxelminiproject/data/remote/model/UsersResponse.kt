package com.example.paxelminiproject.data.remote.model

import com.google.gson.annotations.SerializedName


data class UsersResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incompleteResults")
    val incompleteResults: Boolean,
    val items: List<UserResponse>? = null
)