package com.codedirect.laundry.data.source.remote

import com.codedirect.laundry.data.source.remote.response.ResponseJSON
import com.codedirect.laundry.data.source.remote.response.TransactionsItems
import com.codedirect.laundry.data.source.remote.response.UsersItems
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API {
    @POST("users/insert")
    suspend fun registerUsers(
        @Body data: UsersItems
    ): ResponseJSON

    @POST("users/login")
    suspend fun loginUsers(
        @Body data: UsersItems
    ): ResponseJSON

    @POST("transactions/insert")
    suspend fun postTransaction(
        @Body data: TransactionsItems
    ): ResponseJSON

    @POST("transactions/approved")
    suspend fun postApproved(
        @Body data: TransactionsItems
    ): ResponseJSON

    @GET("transactions")
    suspend fun getTransactions(): ResponseJSON

    @GET("transactions/delete/{id}")
    suspend fun getTransactionsDeleteById(
        @Path("id") id: String
    ): ResponseJSON
}