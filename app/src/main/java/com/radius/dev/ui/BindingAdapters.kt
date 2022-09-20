package com.radius.dev.ui

import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.radius.dev.R


@BindingAdapter("setIcon")
fun MaterialButton.setIcon(status: String) {
    val iconResource = when (status) {
        "apartment" -> R.drawable.apartment
        "condo" -> R.drawable.condo
        "boat" -> R.drawable.boat
        "land" -> R.drawable.land
        "rooms" -> R.drawable.rooms
        "no-room" -> R.drawable.no_room
        "swimming" -> R.drawable.swimming
        "garden" -> R.drawable.garden
        "garage" -> R.drawable.garage
        else -> R.drawable.no_room
    }

    setIconResource(iconResource)
}
