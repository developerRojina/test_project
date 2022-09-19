package com.radius.dev.data.model

import com.squareup.moshi.Json

data class RadiusDTO(
    val facilities: List<FacilityDTO>,
    val exclusions: List<List<ExclusionDTO>>

)

data class FacilityDTO(
    @Json(name = "facility_id") val facilityId: Int,
    val name: String,
    val options: List<OptionDTO>

)

data class OptionDTO(
    val name: String,
    val icon: String,
    val id: Int
)



data class ExclusionDTO(
    @Json(name = "facility_id") val facilityId: Int,
    @Json(name = "options_id") val optionsId: Int,
)

