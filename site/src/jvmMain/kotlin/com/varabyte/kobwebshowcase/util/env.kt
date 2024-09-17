package com.varabyte.kobwebshowcase.util

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

val env = dotenv {
    ignoreIfMissing = true
}

operator fun Dotenv.get(envVariable: EnvVariable): String = get(envVariable.name)

enum class EnvVariable {
    DB_DRIVER,
    DB_PROTOCOL,
    DB_HOST,
    DB_PORT,
    DB_NAME,
    DB_USER,
    DB_PW,
    DB_POOL_MAX
}