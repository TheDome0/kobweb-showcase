package com.varabyte.kobwebshowcase.db

data class Website(
    val id: kotlin.uuid.Uuid,
    val name: String,
    val url: String,
    val description: String,
    val sourceurl: String?,
    val updatedat: kotlinx.datetime.LocalDateTime,
    val status: Boolean,
)
