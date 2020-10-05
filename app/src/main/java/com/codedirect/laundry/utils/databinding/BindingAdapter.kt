package com.codedirect.laundry.utils.databinding

import androidx.databinding.BindingAdapter
import com.codedirect.laundry.R
import com.google.android.material.button.MaterialButton


@BindingAdapter("isTwoToggleChecked")
fun isTwoToggleChecked(view: MaterialButton, boolean: Boolean) {
    if (boolean) {
        view.setBackgroundColor(view.context.resources.getColor(R.color.primaryTextColor))
        view.setTextColor(view.context.resources.getColor(android.R.color.white))
    } else {
        view.setBackgroundColor(view.context.resources.getColor(android.R.color.white))
        view.setTextColor(view.context.resources.getColor(R.color.primaryTextColor))
    }
}