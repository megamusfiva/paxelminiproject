package com.example.paxelminiproject.data.repository

import com.example.paxelminiproject.data.remote.model.UsersResponse
import com.example.paxelminiproject.data.remote.api.ApiService
import com.example.paxelminiproject.utils.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun search(query: String): Resource<UsersResponse> {
        return try {
            val response = apiService.getSearch(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.success(it)
                } ?: Resource.error("Error: query result is null", null)
            } else {
                Resource.error("${response.code()} ${response.message()}", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}
