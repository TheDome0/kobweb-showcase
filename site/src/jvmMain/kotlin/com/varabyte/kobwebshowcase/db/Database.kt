package com.varabyte.kobwebshowcase.db

import com.varabyte.kobwebshowcase.util.EnvVariable
import com.varabyte.kobwebshowcase.util.env
import com.varabyte.kobwebshowcase.util.get
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option
import org.komapper.core.dsl.Meta
import org.komapper.r2dbc.R2dbcDatabase

class Database {
    private val options = ConnectionFactoryOptions.builder()
        .option(ConnectionFactoryOptions.DRIVER, env[EnvVariable.DB_DRIVER])
        .option(ConnectionFactoryOptions.PROTOCOL, env[EnvVariable.DB_PROTOCOL])
        .option(ConnectionFactoryOptions.HOST, env[EnvVariable.DB_HOST])
        .option(ConnectionFactoryOptions.PORT, env[EnvVariable.DB_PORT].toInt())
        .option(ConnectionFactoryOptions.DATABASE, env[EnvVariable.DB_NAME])
        .option(ConnectionFactoryOptions.USER, env[EnvVariable.DB_USER])
        .option(ConnectionFactoryOptions.PASSWORD, env[EnvVariable.DB_PW])
        .option(Option.valueOf("maxSize"), env[EnvVariable.DB_POOL_MAX])
        .build()

    val connection = R2dbcDatabase(options)

    companion object {
        val websites = Meta.website
    }
}