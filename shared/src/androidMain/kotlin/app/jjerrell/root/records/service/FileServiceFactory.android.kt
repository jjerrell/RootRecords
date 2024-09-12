package app.jjerrell.root.records.service

import android.content.Context

actual class FileReaderService(private val context: Context) {
    actual fun readFile(path: String): String? {
        return try {
            context.assets.open(path).bufferedReader().use {
                it.readText()
            }
        } catch (_: Exception) {
            null
        }
    }
}