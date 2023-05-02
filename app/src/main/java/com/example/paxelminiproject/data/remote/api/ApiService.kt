package com.example.paxelminiproject.data.remote.api

import com.example.paxelminiproject.data.remote.model.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search/repositories")
    suspend fun getSearch(
        @Query("q") query: String
    ): Response<UsersResponse>
}
