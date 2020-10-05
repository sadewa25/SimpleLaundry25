package com.codedirect.laundry.ui.adding_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.codedirect.laundry.R
import com.codedirect.laundry.data.source.remote.response.ResponseJSON
import com.codedirect.laundry.data.source.remote.response.TransactionsItems
import com.codedirect.laundry.databinding.FragAddingDataBinding
import com.codedirect.laundry.utils.SessionManager
import com.codedirect.laundry.utils.common.EventObserver
import com.codedirect.laundry.utils.common.Status
import com.codedirect.laundry.utils.findNavController
import com.codedirect.laundry.utils.setFragBinding
import com.codedirect.laundry.utils.successDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddingDataFrag : Fragment() {

    private lateinit var binding: FragAddingDataBinding

    private val model by viewModel<AddingDataVM>()
    private val sessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setFragBinding(R.layout.frag_adding_data, container)
        binding.lifecycleOwner = this
        binding.model = model
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() {
        model.onSubmit.observe(viewLifecycleOwner, EventObserver {
            postTransaction()
        })
    }

    private fun postTransaction() {
        model.postTransaction(
            TransactionsItems(
                nameUsersTransaksi = binding.edAddingName.text.toString(),
                weightTransaksi = binding.edAddingWeight.text.toString(),
                descTransaksi = binding.edAddingDesc.text.toString(),
                idUsers = sessionManager.getIDUser().toString()
            )
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingAdding.visibility = View.GONE
                        resource.data.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        binding.loadingAdding.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.loadingAdding.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: ResponseJSON?) {
        if (users != null) {
            successDialog(getString(R.string.information), getString(R.string.success_process)) {
                findNavController().navigateUp()
                it.dismissWithAnimation()
            }
        }
    }

}