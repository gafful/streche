package com.gafful.streche

expect class Platform() {
    val platform: String
}

internal expect fun printThrowable(t: Throwable)