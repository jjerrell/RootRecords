package app.jjerrell.root.records.service

expect class FileReaderService {
    fun readFile(path: String): String?
}