package com.codedirect.laundry.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.codedirect.laundry.data.source.AppRepository
import com.codedirect.laundry.data.source.remote.response.UsersItems
import com.codedirect.laundry.utils.common.Event
import com.codedirect.laundry.utils.common.Resource
import kotlinx.coroutines.Dispatchers

class LoginVM(private val repository: AppRepository?) : ViewModel() {

    fun getUsers(datas: UsersItems) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository?.loginUsers(datas)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private val _onClickRegister = MutableLiveData<Event<Unit>>()
    val onClickRegister: LiveData<Event<Unit>> = _onClickRegister

    fun onClickRegister() {
        _onClickRegister.value = Event(Unit)
    }

    private val _onClickLogin = MutableLiveData<Event<Unit>>()
    val onClickLogin: LiveData<Event<Unit>> = _onClickLogin

    fun onClickLogin() {
        _onClickLogin.value = Event(Unit)
    }

}