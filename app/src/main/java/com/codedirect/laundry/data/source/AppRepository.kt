package com.codedirect.laundry.data.source

import com.codedirect.laundry.data.source.remote.RemoteRepository
import com.codedirect.laundry.data.source.remote.response.ResponseJSON
import com.codedirect.laundry.data.source.remote.response.TransactionsItems
import com.codedirect.laundry.data.source.remote.response.UsersItems
import com.codedirect.laundry.utils.ContextProvider

class AppRepository(
    private val remoteRepository: RemoteRepository,
    private val coroutineContext: ContextProvider
) : AppDataSource {

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(
            remoteRepository: RemoteRepository
        ): AppRepository = INSTANCE
            ?: synchronized(AppRepository::class.java) {
                AppRepository(
                    remoteRepository,
                    ContextProvider.getInstance()
                ).also {
                    INSTANCE = it
                }
            }
    }

    override suspend fun registerUsers(datas: UsersItems): ResponseJSON =
        remoteRepository.registerUsers(datas)

    override suspend fun loginUsers(datas: UsersItems): ResponseJSON =
        remoteRepository.loginUsers(datas)

    override suspend fun postTransaction(datas: TransactionsItems): ResponseJSON =
        remoteRepository.postTransaction(datas)

    override suspend fun postApproved(datas: TransactionsItems): ResponseJSON =
        remoteRepository.postApproved(datas)

    override suspend fun getTransactions(): ResponseJSON = remoteRepository.getTransactions()
    override suspend fun getTransactionsDeleteById(id: String): ResponseJSON =
        remoteRepository.getTransactionsDeleteById(id)

}