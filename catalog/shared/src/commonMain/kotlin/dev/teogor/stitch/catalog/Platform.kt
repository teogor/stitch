package dev.teogor.stitch.catalog

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform