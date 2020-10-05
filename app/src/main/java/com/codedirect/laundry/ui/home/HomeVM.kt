package com.codedirect.laundry.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.codedirect.laundry.data.source.AppRepository
import com.codedirect.laundry.data.source.remote.response.TransactionsItems
import com.codedirect.laundry.utils.common.Event
import com.codedirect.laundry.utils.common.Resource
import kotlinx.coroutines.Dispatchers

class HomeVM(private val repository: AppRepository?) : ViewModel() {

    private val _dataItems by lazy {
        MutableLiveData<List<TransactionsItems>>().apply {
            value = emptyList()
        }
    }
    private val _dataItemsBackup by lazy {
        MutableLiveData<List<TransactionsItems>>().apply {
            value = emptyList()
        }
    }

    val dataItems: LiveData<List<TransactionsItems>> = _dataItems

    fun setDataBody(item: List<TransactionsItems>) {
        _dataItems.apply {
            value = item
        }
        _dataItemsBackup.apply {
            value = item
        }
    }

    private val _onAddingClick = MutableLiveData<Event<Unit>>()
    val onAddingClick: LiveData<Event<Unit>> = _onAddingClick

    fun onAddingClick() {
        _onAddingClick.value = Event(Unit)
    }

    private val _onItemClick = MutableLiveData<Event<TransactionsItems>>()
    val onItemClick: LiveData<Event<TransactionsItems>> = _onItemClick

    fun onItemClick(data: TransactionsItems) {
        _onItemClick.value = Event(data)
    }

    private val _onLogout = MutableLiveData<Event<Unit>>()
    val onLogout: LiveData<Event<Unit>> = _onLogout

    fun onLogout() {
        _onLogout.value = Event(Unit)
    }

    private val _onItemDelete = MutableLiveData<Event<TransactionsItems>>()
    val onItemDelete: LiveData<Event<TransactionsItems>> = _onItemDelete

    fun onItemDelete(data: TransactionsItems) {
        _onItemDelete.value = Event(data)
    }

    fun getTransactions() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository?.getTransactions()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun postApproved(data: TransactionsItems) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository?.postApproved(data)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getTransactionsDeleteById(id:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository?.getTransactionsDeleteById(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (s.isNullOrEmpty()) {
            _dataItems.apply {
                value = _dataItemsBackup.value
            }
        } else {
            val sample = _dataItems.value?.filter {
                it.nameUsersTransaksi.toString().toLowerCase()
                    .contains(s.toString().toLowerCase()) ||
                        it.weightTransaksi.toString().toLowerCase()
                            .contains(s.toString().toLowerCase())
            }
            _dataItems.apply {
                value = sample
            }
        }
    }
}