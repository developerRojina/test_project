package com.radius.dev.ui

import com.radius.dev.data.model.Facility

sealed class RadiusUiState {
    object Init:RadiusUiState()
    object LoadingFromApi : RadiusUiState()
    data class RadiusData(val items: List<Facility>) : RadiusUiState()
    data class Error(val issue: String) : RadiusUiState()

}