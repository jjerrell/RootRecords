package app.jjerrell.root.records.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(private val context: Context) {
    actual fun createBuilder(): RoomDatabase.Builder<RootRecordsRoomDatabase> {
        return Room.databaseBuilder<RootRecordsRoomDatabase>(
            context = context.applicationContext,
            name = "root_records.db",
            factory = { RootRecordsRoomDatabase::class.instantiateImpl() })
    }
}
