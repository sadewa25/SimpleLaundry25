package com.codedirect.laundry.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.codedirect.laundry.R
import com.codedirect.laundry.data.source.remote.response.ResponseJSON
import com.codedirect.laundry.data.source.remote.response.UsersItems
import com.codedirect.laundry.databinding.FragLoginBinding
import com.codedirect.laundry.utils.*
import com.codedirect.laundry.utils.common.EventObserver
import com.codedirect.laundry.utils.common.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFrag : Fragment() {

    private lateinit var binding: FragLoginBinding

    private val model by viewModel<LoginVM>()
    private val sessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setFragBinding(R.layout.frag_login, container)
        binding.lifecycleOwner = this
        binding.model = model
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbar()
        setupObservers()
        checkLogin()
    }

    private fun setupObservers() {
        model.onClickRegister.observe(viewLifecycleOwner, EventObserver {
            navigateToRegister()
        })
        model.onClickLogin.observe(viewLifecycleOwner, EventObserver {
            login()
        })
    }

    private fun navigateToRegister() {
        val actions = LoginFragDirections.actionLoginFragmentToRegisterFrag()
        findNavController().navigate(actions)
    }

    private fun checkLogin() {
        if (sessionManager.getLogin() == true)
            navigateToHome()
    }

    private fun navigateToHome() {
        findNavController().navigate(LoginFragDirections.actionLoginFragmentToHomeFrag())
    }

    private fun login() {
        model.getUsers(
            UsersItems(
                emailUsers = binding.edLoginUsername.text.toString(),
                passwordUsers = binding.edLoginPassword.text.toString()
            )
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loginLoading.visibility = View.GONE
                        resource.data.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        binding.loginLoading.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.loginLoading.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(
        it: ResponseJSON?
    ) {
        if (it != null && !it.users.isNullOrEmpty()) {
            val item = it
            successDialog(getString(R.string.information), getString(R.string.success_process)) {
                sessionManager.setLogin(true)
                sessionManager.setIDUser(item.users?.get(0)?.idUsers.toString())
                sessionManager.setIDUserType(item.users?.get(0)?.idUsersType.toString())
                navigateToHome()
                it.dismissWithAnimation()
            }
        }
    }

}
