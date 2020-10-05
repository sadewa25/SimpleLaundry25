package com.codedirect.laundry.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.codedirect.laundry.data.source.remote.response.TransactionsItems
import com.codedirect.laundry.databinding.ItemTransactionsBinding
import com.codedirect.laundry.utils.databinding.AppRecyclerView

class HomeRvAdapter(viewModel: HomeVM) :
    AppRecyclerView<HomeVM, ItemTransactionsBinding, TransactionsItems>(
        viewModel,
        DiffUtilList()
    ) {

    override fun onCreateViewBindingHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewHolder<ItemTransactionsBinding> =
        ViewHolder(ItemTransactionsBinding.inflate(inflater, parent, false))

    override fun onPrepareBindViewHolder(
        binding: ItemTransactionsBinding,
        viewModel: HomeVM,
        model: TransactionsItems
    ) {
        binding.data = model
        binding.model = viewModel
    }

}

class DiffUtilList : DiffUtil.ItemCallback<TransactionsItems>() {

    override fun areItemsTheSame(oldItem: TransactionsItems, newItem: TransactionsItems): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TransactionsItems, newItem: TransactionsItems): Boolean {
        return oldItem.idApproval == newItem.idApproval
    }

}
