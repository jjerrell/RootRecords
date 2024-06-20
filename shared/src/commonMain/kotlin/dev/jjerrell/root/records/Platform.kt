package dev.jjerrell.root.records

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform