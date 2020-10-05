package com.codedirect.laundry.utils

import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.afollestad.materialdialogs.MaterialDialog
import com.codedirect.laundry.R

fun Fragment.findNavController(): NavController =
    NavHostFragment.findNavController(this)

fun <T : ViewDataBinding?> AppCompatActivity.setActBinding(layoutId: Int): T {
    return DataBindingUtil.setContentView<T>(this, layoutId)
}

fun <T : ViewDataBinding?> Fragment.setFragBinding(layoutId: Int, container: ViewGroup?): T {
    return DataBindingUtil.inflate<T>(layoutInflater, layoutId, container, false)
}


fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Fragment.showToolbar(message: String) {
    (this.activity as AppCompatActivity).supportActionBar?.show()
    (this.activity as AppCompatActivity).supportActionBar?.title = message
}

fun Fragment.hideToolbar() {
    (this.activity as AppCompatActivity).supportActionBar?.hide()
}

fun Fragment.confirmationDialog(
    type: Int,
    title: String,
    content: String,
    listenerPositive: (SweetAlertDialog) -> Unit
) {
    SweetAlertDialog(context, type)
        .setTitleText(title)
        .setContentText(content)
        .setConfirmText(context?.getString(R.string.yes_))
        .setConfirmClickListener {
            listenerPositive(it)
        }
        .setCancelButton(
            context?.getString(R.string.no_)
        ) {
            it.dismissWithAnimation()
        }
        .show()
}

fun Fragment.confirmationMaterialDialog(
    title: String,
    message: String,
    listenerPositive: (MaterialDialog) -> Unit
) {
    MaterialDialog(requireContext()).show {
        title(text = title)
        message(text = message)
        positiveButton(text = context.resources?.getString(R.string.yes_)) {
            listenerPositive(it)
        }
        negativeButton(text = context.resources?.getString(R.string.no_)) {
            it.dismiss()
        }
    }
}

fun Fragment.successDialog(
    title: String,
    content: String,
    listenerPositive: (SweetAlertDialog) -> Unit
) {
    SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE).setTitleText(title)
        .setContentText(content)
        .setConfirmText(getString(R.string.ok_))
        .setConfirmClickListener {
            listenerPositive(it)
        }
        .showCancelButton(false)
        .show()
}

fun Fragment.errorDialog(
    title: String,
    content: String
) {
    SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
        .setTitleText(title)
        .setContentText(content)
        .show()
}