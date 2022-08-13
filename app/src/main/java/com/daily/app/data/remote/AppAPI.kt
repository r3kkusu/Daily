package com.daily.app.data.remote

import com.daily.app.common.Constants
import com.daily.app.data.remote.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AppAPI {

    @Headers(
        "X-RapidAPI-Key: ${Constants.RAPID_KEY}",
        "X-RapidAPI-Host: ${Constants.RAPID_HOST}"
    )
    @GET("/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("lang") lang: String,
        @Query("limit") limit: String
    ): ResponseDto

    @Headers(
        "X-RapidAPI-Key: ${Constants.RAPID_KEY}",
        "X-RapidAPI-Host: ${Constants.RAPID_HOST}"
    )
    @GET("/topic-headlines")
    suspend fun getTopicHeadlines(
        @Query("topic") topic: String,
        @Query("country") country: String,
        @Query("lang") lang: String,
        @Query("limit") limit: String
    ): ResponseDto

    @Headers(
        "X-RapidAPI-Key: ${Constants.RAPID_KEY}",
        "X-RapidAPI-Host: ${Constants.RAPID_HOST}"
    )
    @GET("/search")
    suspend fun searchArticles(
        @Query("q") q: String,
        @Query("country") country: String,
        @Query("lang") lang: String,
        @Query("limit") limit: String,
    ): ResponseDto

    @Headers(
        "X-RapidAPI-Key: ${Constants.RAPID_KEY}",
        "X-RapidAPI-Host: ${Constants.RAPID_HOST}"
    )
    @GET("/top-headlines")
    suspend fun getGeolocation(
        @Query("geo") geo: String,
        @Query("country") country: String,
        @Query("lang") lang: String,
        @Query("limit") limit: String
    ): ResponseDto


}