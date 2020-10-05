package com.codedirect.laundry.ui.splashscreen


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codedirect.laundry.R
import com.codedirect.laundry.utils.SessionManager
import com.codedirect.laundry.utils.Utils
import com.codedirect.laundry.utils.findNavController
import com.codedirect.laundry.utils.hideToolbar
import com.novoda.merlin.*

class SplashScreenFrag : Fragment(), Connectable, Disconnectable, Bindable {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_splash_screen, container, false)
    }

    private val merlin: Merlin by lazy {
        Merlin.Builder().withConnectableCallbacks().withDisconnectableCallbacks()
            .withBindableCallbacks().build(context)
    }

    private val sessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbar()
    }

    override fun onResume() {
        super.onResume()
        merlin.bind()
        merlin.bind()
        merlin.registerConnectable(this)
        merlin.registerBindable(this)
        merlin.registerDisconnectable(this)
    }

    override fun onPause() {
        merlin.unbind()
        super.onPause()
    }

    private fun splashScreenRun() {
        Handler().postDelayed({
            if (sessionManager.getLogin() == true)
                navigateToHome()
            else
                navigateToLogin()
        }, 1000)
    }

    private fun navigateToHome() {
        findNavController().navigate(SplashScreenFragDirections.actionSplashScreenFragmentToHomeFrag())
    }

    private fun navigateToLogin() {
        val actions = SplashScreenFragDirections.actionSplashScreenFragmentToLoginFragment()
        findNavController().navigate(actions)
    }

    override fun onConnect() {}
    override fun onDisconnect() {}

    override fun onBind(networkStatus: NetworkStatus?) {
        if (networkStatus != null) {
            if (networkStatus.isAvailable) {
                splashScreenRun()
            } else {
                Utils().toast(requireContext(), getString(R.string.network_unavailable))
                activity?.finish()
            }
        }
    }

}
