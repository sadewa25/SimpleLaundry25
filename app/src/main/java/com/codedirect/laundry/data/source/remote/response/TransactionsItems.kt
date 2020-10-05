package com.codedirect.laundry.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionsItems(

    @field:SerializedName("name_users_transaksi")
    val nameUsersTransaksi: String? = null,

    @field:SerializedName("desc_transaksi")
    val descTransaksi: String? = null,

    @field:SerializedName("weight_transaksi")
    val weightTransaksi: String? = null,

    @field:SerializedName("id_users")
    val idUsers: String? = null,

    @field:SerializedName("id_users_transaksi")
    val idUsersTransaksi: String? = null,

    @field:SerializedName("id_users_transaction")
    val idUsersTransaction: String? = null,

    @field:SerializedName("id_status_transaction")
    val idStatusTransaction: String? = null,

    @field:SerializedName("id_approval")
    val idApproval: String? = null
)