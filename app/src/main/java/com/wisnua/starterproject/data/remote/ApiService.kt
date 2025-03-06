package com.wisnua.starterproject.data.remote

import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.model.RepoResponseItem
import com.wisnua.starterproject.domain.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): SearchResponse

    @GET("users/{username}")
    suspend fun getDetail(
        @Path("username") username: String
    ): DetailResponse

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): List<RepoResponseItem>


}
