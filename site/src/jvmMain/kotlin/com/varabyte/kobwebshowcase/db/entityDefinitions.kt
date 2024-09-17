package com.varabyte.kobwebshowcase.db

import org.komapper.annotation.KomapperColumn
import org.komapper.annotation.KomapperEntityDef
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntityDef(Website::class)
@KomapperTable("website")
data class WebsiteDef(
    @KomapperId @KomapperColumn("id") val id: Nothing,
    @KomapperColumn("name") val name: Nothing,
    @KomapperColumn("url") val url: Nothing,
    @KomapperColumn("description") val description: Nothing,
    @KomapperColumn("sourceurl") val sourceurl: Nothing,
    @KomapperColumn("updatedat") val updatedat: Nothing,
    @KomapperColumn("status") val status: Nothing,
)
