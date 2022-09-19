package com.radius.dev

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radius.dev.data.model.Option
import com.radius.dev.data.source.RadiusRepository
import com.radius.dev.ui.RadiusUiState
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

    fun isSelectionValid(option: Option): Boolean {
        if (selectedOptions.isEmpty()) {
            selectedOptions.add(option)
            return true
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
                    true
                } else {
                    _state.value =
                        RadiusUiState.Error("These two options are not compatible with each other")

                    false
                }

            } else {
                _state.value =
                    if (optionsWithFacilityId.isEmpty())
                        RadiusUiState.Error("Please select the option from different facility type")
                    else
                        RadiusUiState.Error("Please select different option")

                return false
            }

        }
    }

    fun clearSelections() {
        selectedOptions.clear()
    }


}