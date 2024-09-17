package com.varabyte.kobwebshowcase.util

import arrow.core.Either
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.setBodyText
import com.varabyte.kobwebshowcase.db.Website
import com.varabyte.kobwebshowcase.errors.DomainError
import com.varabyte.kobwebshowcase.errors.Duplicate
import com.varabyte.kobwebshowcase.errors.NotFound
import kotlinx.datetime.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

object Time {
    fun instant(): Instant =
        LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS).toInstant(java.time.ZoneOffset.UTC)
            .toKotlinInstant()

    fun localDate() = Clock.System.todayIn(TimeZone.currentSystemDefault())
    fun localDateTime() = LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS).toKotlinLocalDateTime()
}

inline fun <reified T : Any> Either<DomainError, T>.respond(ctx: ApiContext) {
    when (this) {
        is Either.Left -> when (this.value) {
            Duplicate -> ctx.res.status = 409
            NotFound -> ctx.res.status = 404
        }

        is Either.Right -> {
            ctx.res.contentType = "application/json"
            ctx.res.status = 200
            ctx.res.setBodyText(Json.encodeToString(this.value))
        }
    }
}

fun Website.toModel() = com.varabyte.kobwebshowcase.models.Website(
    id = id,
    name = name,
    url = url,
    description = description,
    sourceUrl = sourceurl,
)