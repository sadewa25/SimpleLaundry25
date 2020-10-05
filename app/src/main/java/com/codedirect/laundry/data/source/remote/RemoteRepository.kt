package com.codedirect.laundry.data.source.remote

import com.codedirect.laundry.data.source.remote.response.TransactionsItems
import com.codedirect.laundry.data.source.remote.response.UsersItems


class RemoteRepository(private val apiService: RetrofitClient) {
    companion object {
        private var INSTANCE: RemoteRepository? = null

        fun getInstance(apiService: RetrofitClient): RemoteRepository {
            if (INSTANCE == null) {
                INSTANCE = RemoteRepository(apiService)
            }
            return INSTANCE as RemoteRepository
        }
    }

    suspend fun registerUsers(datas: UsersItems) = apiService.response().registerUsers(datas)
    suspend fun loginUsers(datas: UsersItems) = apiService.response().loginUsers(datas)
    suspend fun postTransaction(datas: TransactionsItems) = apiService.response().postTransaction(datas)
    suspend fun postApproved(datas: TransactionsItems) = apiService.response().postApproved(datas)
    suspend fun getTransactions() = apiService.response().getTransactions()
    suspend fun getTransactionsDeleteById(id:String) = apiService.response().getTransactionsDeleteById(id)
}