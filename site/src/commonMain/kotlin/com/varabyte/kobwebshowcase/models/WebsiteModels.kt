package com.varabyte.kobwebshowcase.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class Website(
    @Contextual
    val id: Uuid,
    val name: String,
    val url: String,
    val description: String,
    val sourceUrl: String?,
)

@Serializable
data class WebsiteList(
    val websites: List<Website>,
    val hasNext: Boolean,
)

@Serializable
data class NewWebsite(
    val name: String,
    val url: String,
    val description: String,
    val sourceUrl: String?,
)