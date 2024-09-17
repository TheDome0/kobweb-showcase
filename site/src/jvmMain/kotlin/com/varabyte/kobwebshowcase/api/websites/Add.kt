package com.varabyte.kobwebshowcase.api.websites

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.readBodyText
import com.varabyte.kobwebshowcase.models.NewWebsite
import com.varabyte.kobwebshowcase.repositories.WebsiteRepo
import com.varabyte.kobwebshowcase.util.Time
import com.varabyte.kobwebshowcase.util.respond
import kotlinx.serialization.json.Json
import kotlin.uuid.Uuid

@Api
suspend fun addWebsite(ctx: ApiContext) {
    if (ctx.req.method != HttpMethod.POST) return

    val new: NewWebsite = ctx.req.readBodyText()?.let { Json.decodeFromString(it) }
        ?: return run { ctx.res.status = 422 }

    val repo = ctx.data.getValue<WebsiteRepo>()

    repo.createWebsite(
        id = Uuid.random(),
        url = new.url,
        name = new.name,
        description = new.description,
        sourceUrl = new.sourceUrl,
        updatedAt = Time.localDateTime(),
        status = false
    ).respond(ctx)
}