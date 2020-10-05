package com.codedirect.laundry.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codedirect.laundry.R
import com.codedirect.laundry.data.source.remote.response.ResponseJSON
import com.codedirect.laundry.data.source.remote.response.TransactionsItems
import com.codedirect.laundry.databinding.FragHomeBinding
import com.codedirect.laundry.utils.*
import com.codedirect.laundry.utils.common.EventObserver
import com.codedirect.laundry.utils.common.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFrag : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragHomeBinding

    private val model by viewModel<HomeVM>()
    private val sessionManager by lazy {
        SessionManager(requireContext())
    }
    private val _listDataAdapter by lazy {
        HomeRvAdapter(
            model
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setFragBinding(R.layout.frag_home, container)
        binding.lifecycleOwner = this
        binding.model = model
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupRefresh()
        setupRvAdapter()
        getTransactions()
    }

    private fun setupRefresh() {
        binding.sfItemTransactions.setOnRefreshListener(this)
    }

    private fun setupRvAdapter() {
        binding.rvItemTransactions.adapter = _listDataAdapter
    }

    private fun setupObservers() {
        if (sessionManager.getIdUserType().equals("2")) {
            binding.fabHome.visibility = View.VISIBLE
            model.onAddingClick.observe(viewLifecycleOwner, EventObserver {
                navigateToAddingData()
            })
            model.onItemClick.observe(viewLifecycleOwner, EventObserver {
                val item = it
                if (it.idApproval == "1")
                    confirmationMaterialDialog(
                        getString(R.string.information),
                        getString(R.string.isFinished)
                    ) {
                        postApproved(
                            TransactionsItems(
                                idUsersTransaction = item.idUsersTransaksi,
                                idStatusTransaction = "2"
                            )
                        )
                    }
                else
                    errorDialog(getString(R.string.information), getString(R.string.finished))
            })
            model.onItemDelete.observe(viewLifecycleOwner, EventObserver {
                val item = it
                confirmationMaterialDialog(
                    getString(R.string.information),
                    getString(R.string.confirmation_delete)
                ) {
                    getTransactionsDeleteById(item.idUsersTransaksi.toString())
                }
            })
        }
        model.onLogout.observe(viewLifecycleOwner, EventObserver {
            confirmationMaterialDialog(
                getString(R.string.information),
                getString(R.string.confirmation_exit)
            ) {
                sessionManager.setLogin(false)
                findNavController().navigateUp()
            }
        })
    }

    private fun navigateToAddingData() {
        findNavController().navigate(HomeFragDirections.actionHomeFragToAddingDataFrag())
    }

    private fun getTransactions() {
        model.getTransactions().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingTransactions.visibility = View.GONE
                        resource.data.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        binding.loadingTransactions.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.loadingTransactions.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun getTransactionsDeleteById(id: String) {
        model.getTransactionsDeleteById(id).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingTransactions.visibility = View.GONE
                        resource.data.let { users -> retrieveDelete(users) }
                    }
                    Status.ERROR -> {
                        binding.loadingTransactions.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.loadingTransactions.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveDelete(users: ResponseJSON?) {
        if (users != null) {
            getTransactions()
        }
    }

    private fun retrieveApproved(users: ResponseJSON?) {
        if (users != null) {
            getTransactions()
        }
    }

    private fun postApproved(data: TransactionsItems) {
        model.postApproved(data).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingTransactions.visibility = View.GONE
                        resource.data.let { users -> retrieveApproved(users) }
                    }
                    Status.ERROR -> {
                        binding.loadingTransactions.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.loadingTransactions.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: ResponseJSON?) {
        if (users != null) {
            model.setDataBody(users.transactions as List<TransactionsItems>)
        }
    }

    override fun onRefresh() {
        binding.sfItemTransactions.isRefreshing = false
        getTransactions()
    }

}