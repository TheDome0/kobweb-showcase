package com.varabyte.kobwebshowcase.api.websites

import arrow.core.toOption
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobwebshowcase.models.WebsiteList
import com.varabyte.kobwebshowcase.repositories.WebsiteRepo
import com.varabyte.kobwebshowcase.util.respond
import com.varabyte.kobwebshowcase.util.toModel
import kotlin.uuid.Uuid

@Api
suspend fun listWebsites(ctx: ApiContext) {
    if (ctx.req.method != HttpMethod.GET) return

    val afterId = ctx.req.params["afterId"]?.let { Uuid.parse(it) }
    val repo = ctx.data.getValue<WebsiteRepo>()

    repo.websites(51, afterId.toOption(), true.toOption()).map { list ->
        WebsiteList(list.map { it.toModel() }, list.size == 51)
    }.respond(ctx)
}