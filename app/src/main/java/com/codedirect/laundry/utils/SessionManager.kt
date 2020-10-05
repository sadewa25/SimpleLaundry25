package com.codedirect.laundry.utils

import android.content.Context
import android.content.SharedPreferences
import com.codedirect.laundry.R

class SessionManager(private val context: Context?) {

    // Shared pref mode
    val PRIVATE_MODE = 0

    // Sharedpref file name
    private val PREF_NAME = context?.resources?.getString(R.string.app_name)

    var pref: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor? = pref?.edit()

    private val is_login = "is_login"
    fun setLogin(check: Boolean) {
        editor?.putBoolean(is_login, check)
        editor?.apply()
    }

    fun getLogin(): Boolean? {
        return pref?.getBoolean(is_login, false);
    }

    private val idUser = "_id"
    fun setIDUser(data: String) {
        editor?.putString(idUser, data)
        editor?.apply()
    }

    fun getIDUser(): String? {
        return pref?.getString(idUser, ""); }

    private val idUserType = "IDUserType"
    fun setIDUserType(data: String) {
        editor?.putString(idUserType, data)
        editor?.apply()
    }

    fun getIdUserType(): String? {
        return pref?.getString(idUserType, ""); }


}