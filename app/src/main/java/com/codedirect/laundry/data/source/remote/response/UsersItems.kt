package com.codedirect.laundry.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UsersItems(
    @field:SerializedName("email_users")
    val emailUsers: String? = null,

    @field:SerializedName("id_users")
    val idUsers: String? = null,

    @field:SerializedName("alamat_users")
    val alamatUsers: String? = null,

    @field:SerializedName("password_users")
    val passwordUsers: String? = null,

    @field:SerializedName("id_users_type")
    val idUsersType: String? = null
)