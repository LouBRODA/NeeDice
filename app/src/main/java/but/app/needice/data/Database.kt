package but.app.needice.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import but.app.needice.dao.DaoColor
import but.app.needice.dao.DaoDice
import but.app.needice.entity.ColorEntity
import but.app.needice.entity.DiceEntity

@androidx.room.Database(entities = arrayOf(ColorEntity::class, DiceEntity::class), version = 1)
abstract class Database: RoomDatabase() {

    abstract fun daoColor(): DaoColor
    abstract fun daoDice(): DaoDice

    companion object {
        private var INSTANCE: Database? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, Database::class.java, "Database").build()
                INSTANCE = instance
                instance
            }
    }
}