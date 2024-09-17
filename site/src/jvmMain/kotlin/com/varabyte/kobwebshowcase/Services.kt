package com.varabyte.kobwebshowcase

import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import com.varabyte.kobwebshowcase.db.Database
import com.varabyte.kobwebshowcase.repositories.KomapperWebsiteRepo

@InitApi
fun init(ctx: InitApiContext) {
    ctx.data.add(KomapperWebsiteRepo(Database().connection))
}