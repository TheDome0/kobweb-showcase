package com.varabyte.kobwebshowcase.repositories

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.Option
import com.varabyte.kobwebshowcase.db.Website
import com.varabyte.kobwebshowcase.errors.DomainError
import com.varabyte.kobwebshowcase.errors.Duplicate
import com.varabyte.kobwebshowcase.errors.NotFound
import kotlin.uuid.Uuid

interface WebsiteRepo {
    suspend fun websites(
        limit: Int,
        afterId: Option<Uuid>,
        status: Option<Boolean>,
    ): Either<NotFound, NonEmptyList<Website>>

    suspend fun createWebsite(
        id: Uuid,
        name: String,
        url: String,
        description: String,
        sourceUrl: String?,
        updatedAt: kotlinx.datetime.LocalDateTime,
        status: Boolean,
    ): Either<Duplicate, Website>

    suspend fun updateWebsite(
        id: Uuid,
        updatedAt: kotlinx.datetime.LocalDateTime,
        name: Option<String>,
        url: Option<String>,
        description: Option<String>,
        sourceUrl: Option<String?>,
        status: Option<Boolean>,
    ): Either<DomainError, Website>
}