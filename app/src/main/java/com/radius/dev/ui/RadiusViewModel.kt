package com.radius.dev.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radius.dev.data.model.Option
import com.radius.dev.data.source.RadiusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RadiusViewModel @Inject constructor(private val radiusRepository: RadiusRepository) :
    ViewModel() {


    val selectedOptions: MutableList<Option> = mutableListOf()

    private val _state = MutableStateFlow<RadiusUiState>(RadiusUiState.Init)
    val state: StateFlow<RadiusUiState> = _state

    init {
        viewModelScope.launch {
            radiusRepository.getRadiusData().collect {
                _state.value = RadiusUiState.RadiusData(it)
            }
        }
    }

    fun isSelectionValid(option: Option): Pair<Boolean, String> {
        if (selectedOptions.isEmpty()) {
            selectedOptions.add(option)
            return Pair(true, "")
        } else {
            val optionsWithId = selectedOptions.filter {
                option.id == it.id
            }
            val optionsWithFacilityId =
                selectedOptions.filter {
                    option.facilityId == it.facilityId
                }


            if (optionsWithId.isEmpty() && optionsWithFacilityId.isEmpty()) {

                val exc: List<Boolean> = option.exclusions.map { ex ->
                    val op = selectedOptions.filter { opt ->
                        opt.id == ex
                    }
                    op.isEmpty()
                }

                val canInsert = exc.filter { it }

                return if (canInsert.size == exc.size) {
                    selectedOptions.add(option)
                    Pair(true, "")
                } else {
                    Pair(false, "These two options are not compatible with each other")

                }

            } else {
                val msg =
                    if (optionsWithFacilityId.isEmpty())
                        "Please select the option from different facility type"
                    else
                        "Please select different option"

                return Pair(false, msg)
            }

        }
    }

    fun clearSelections() {
        selectedOptions.clear()
    }


}