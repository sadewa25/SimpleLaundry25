package com.codedirect.laundry.data.source

import com.codedirect.laundry.data.source.remote.response.ResponseJSON
import com.codedirect.laundry.data.source.remote.response.TransactionsItems
import com.codedirect.laundry.data.source.remote.response.UsersItems

interface AppDataSource {
    suspend fun registerUsers(datas: UsersItems) : ResponseJSON
    suspend fun loginUsers(datas: UsersItems) : ResponseJSON
    suspend fun postTransaction(datas: TransactionsItems) : ResponseJSON
    suspend fun postApproved(datas: TransactionsItems) : ResponseJSON
    suspend fun getTransactions() : ResponseJSON
    suspend fun getTransactionsDeleteById(id:String) : ResponseJSON
}