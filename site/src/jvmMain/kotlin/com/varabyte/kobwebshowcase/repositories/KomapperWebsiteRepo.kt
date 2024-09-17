package com.varabyte.kobwebshowcase.repositories

import arrow.core.*
import com.varabyte.kobwebshowcase.db.Database
import com.varabyte.kobwebshowcase.db.Website
import com.varabyte.kobwebshowcase.errors.Duplicate
import com.varabyte.kobwebshowcase.errors.NotFound
import kotlinx.datetime.LocalDateTime
import org.komapper.core.UniqueConstraintException
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.operator.asc
import org.komapper.r2dbc.R2dbcDatabase
import kotlin.uuid.Uuid

class KomapperWebsiteRepo(private val db: R2dbcDatabase) : WebsiteRepo {
    override suspend fun websites(
        limit: Int,
        afterId: Option<Uuid>,
        status: Option<Boolean>,
    ) = db.withTransaction {
        db.runQuery {
            QueryDsl.from(Database.websites).where {
                afterId.map { Database.websites.id eq it }
                status.map { Database.websites.status eq it }
            }.orderBy(Database.websites.id.asc()).limit(limit)
        }.toNonEmptyListOrNull()?.right() ?: NotFound.left()
    }

    override suspend fun createWebsite(
        id: Uuid,
        name: String,
        url: String,
        description: String,
        sourceUrl: String?,
        updatedAt: LocalDateTime,
        status: Boolean,
    ) = db.withTransaction {
        db.runQuery {
            QueryDsl.insert(Database.websites).onDuplicateKeyIgnore(Database.websites.url).executeAndGet(
                Website(
                    id = id,
                    name = name,
                    url = url,
                    description = description,
                    sourceurl = sourceUrl,
                    updatedat = updatedAt,
                    status = status
                )
            )
        }?.right() ?: Duplicate.left()
    }

    override suspend fun updateWebsite(
        id: Uuid,
        updatedAt: LocalDateTime,
        name: Option<String>,
        url: Option<String>,
        description: Option<String>,
        sourceUrl: Option<String?>,
        status: Option<Boolean>,
    ) = Either.catch {
        db.withTransaction {
            db.runQuery {
                QueryDsl.update(Database.websites).set {
                    Database.websites.updatedat eq updatedAt
                    name.map { Database.websites.name eq it }
                    url.map { Database.websites.url eq it }
                    description.map { Database.websites.description eq it }
                    sourceUrl.map { Database.websites.sourceurl eq it }
                    status.map { Database.websites.status eq it }
                }.where {
                    Database.websites.id eq id
                }.returning()
            }
        }
    }.mapLeft {
        if (it is UniqueConstraintException) Duplicate else throw it
    }.flatMap { it.singleOrNull()?.right() ?: NotFound.left() }
}