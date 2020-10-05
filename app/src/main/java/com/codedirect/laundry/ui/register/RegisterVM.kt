package com.codedirect.laundry.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.codedirect.laundry.data.source.AppRepository
import com.codedirect.laundry.data.source.remote.response.UsersItems
import com.codedirect.laundry.utils.common.Event
import com.codedirect.laundry.utils.common.Resource
import kotlinx.coroutines.Dispatchers

class RegisterVM (private val repository: AppRepository?) : ViewModel() {

    private val _onClickRegister = MutableLiveData<Event<Unit>>()
    val onClickRegister: LiveData<Event<Unit>> = _onClickRegister

    fun onClickRegister() {
        _onClickRegister.value = Event(Unit)
    }

    fun registerUsers(datas: UsersItems) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository?.registerUsers(datas)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}