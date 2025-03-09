package com.example.plm.Network

import com.example.plm.Network.Request.RequestCreateUser
import com.example.plm.Network.Response.ResponseCreateUser
import com.example.plm.Network.Response.ResponseGetCountries
import com.example.plm.Network.Response.ResponseGetProfessions
import com.example.plm.Network.Response.ResponseGetStates
import com.example.plm.Network.Response.ResponseProducts
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("getProfessions")
    suspend fun getProfessions(): ResponseGetProfessions

    @GET("getCountries")
    suspend fun getCountries(): ResponseGetCountries

    @GET("getStateByCountry")
    suspend fun getStates(@Query("countryId") countryId: String): ResponseGetStates

    @POST("saveMobileLocationAppClient")
    suspend fun createAccount(@Body request: RequestCreateUser): ResponseCreateUser

    @GET("https://dev.plmconnection.com/plmservices/RestPLMPharmaSearchEngine/RestPharmaSearchEngine.svc/getDrugs")
    suspend fun getProducts(@Query("code") codeUser: String, @Query("countryId") countryId: String, @Query("editionId") editionId: String, @Query("drug") drug: String): ResponseProducts

    @GET("https://run.mocky.io/v3/7612b18c-df96-4d01-8f08-a09b71c7c781")
    suspend fun getProductsMocky(): ResponseProducts

}