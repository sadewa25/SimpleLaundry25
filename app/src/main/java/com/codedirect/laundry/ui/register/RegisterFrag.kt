package com.codedirect.laundry.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.codedirect.laundry.R
import com.codedirect.laundry.data.source.remote.response.ResponseJSON
import com.codedirect.laundry.data.source.remote.response.UsersItems
import com.codedirect.laundry.databinding.FragRegisterBinding
import com.codedirect.laundry.utils.SessionManager
import com.codedirect.laundry.utils.common.EventObserver
import com.codedirect.laundry.utils.common.Status
import com.codedirect.laundry.utils.findNavController
import com.codedirect.laundry.utils.setFragBinding
import com.codedirect.laundry.utils.successDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFrag : Fragment() {

    private lateinit var binding: FragRegisterBinding

    private val model by viewModel<RegisterVM>()
    private val sessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setFragBinding(R.layout.frag_register, container)
        binding.lifecycleOwner = this
        binding.model = model
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() {
        model.onClickRegister.observe(viewLifecycleOwner, EventObserver {
            postRegisters()
        })
    }

    private fun postRegisters() {
        model.registerUsers(
            UsersItems(
                emailUsers = binding.edRegisterEmail.text.toString(),
                passwordUsers = binding.edRegisterPassword.text.toString(),
                alamatUsers = binding.edRegisterAddress.text.toString()
            )
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loadingRegister.visibility = View.GONE
                        resource.data.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        binding.loadingRegister.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.loadingRegister.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: ResponseJSON?) {
        if (users != null) {
            if (users.message == getString(R.string.success_)) {
                successDialog(
                    getString(R.string.information),
                    getString(R.string.success_process)
                ) {
                    findNavController().navigateUp()
                    it.dismissWithAnimation()
                }
            }
        }
    }

}