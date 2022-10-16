package com.kaftanchikova.entrantandroid.api

import com.kaftanchikova.entrantandroid.model.Entrant
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EntrantApi {

    @GET("entrants")
    suspend fun getEntrants(): Response<List<Entrant>>

    @POST("entrant")
    suspend fun addEntrant(@Body entrant: Entrant)

    @GET("entrant/enroll/status/{id}")
    suspend fun enrollEntrant(@Path("id") id: Long): Response<List<Entrant>>

    @GET("entrant/delete/{id}")
    suspend fun deleteEntrant(@Path("id") id: Long): Response<List<Entrant>>

}