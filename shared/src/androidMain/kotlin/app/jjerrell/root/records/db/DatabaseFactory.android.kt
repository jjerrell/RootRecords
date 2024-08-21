package app.jjerrell.root.records.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(private val context: Context) {
    actual fun createBuilder(): RoomDatabase.Builder<RootRecordsRoomDatabase> {
        val path = context.getDatabasePath("RootRecords.db").absolutePath
        return Room.databaseBuilder<RootRecordsRoomDatabase>(
            context = context.applicationContext,
            name = path,
            factory = { RootRecordsRoomDatabase::class.instantiateImpl() }
        ).enableMultiInstanceInvalidation()
    }
}
