package com.varabyte.kobwebshowcase.errors

sealed interface DomainError

data object NotFound : DomainError
data object Duplicate : DomainError