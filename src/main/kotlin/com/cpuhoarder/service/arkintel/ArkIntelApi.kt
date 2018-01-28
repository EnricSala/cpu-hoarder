package com.cpuhoarder.service.arkintel

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArkIntelApi {

    @GET("/search/AutoComplete")
    fun search(@Query("term") name: String): Call<List<SearchResult>>

    @GET("/products/{id}")
    fun getSpecs(@Path("id") arkIntelId: String): Call<ResponseBody>

}
