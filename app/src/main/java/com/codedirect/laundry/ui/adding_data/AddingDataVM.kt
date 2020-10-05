package com.codedirect.laundry.ui.adding_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.codedirect.laundry.data.source.AppRepository
import com.codedirect.laundry.data.source.remote.response.TransactionsItems
import com.codedirect.laundry.data.source.remote.response.UsersItems
import com.codedirect.laundry.utils.common.Event
import com.codedirect.laundry.utils.common.Resource
import kotlinx.coroutines.Dispatchers

class AddingDataVM (private val repository: AppRepository?) : ViewModel() {

    private val _onSubmit = MutableLiveData<Event<Unit>>()
    val onSubmit: LiveData<Event<Unit>> = _onSubmit

    fun onSubmit() {
        _onSubmit.value = Event(Unit)
    }

    fun postTransaction(datas: TransactionsItems) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository?.postTransaction(datas)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}