package but.app.needice.data.database

import androidx.room.RoomDatabase
import but.app.needice.data.dao.DaoColor
import but.app.needice.data.dao.DaoDice
import but.app.needice.data.entity.ColorEntity
import but.app.needice.data.entity.DiceEntity

@androidx.room.Database(entities = arrayOf(ColorEntity::class, DiceEntity::class), version = 1)
abstract class Database: RoomDatabase() {

    abstract fun daoColor(): DaoColor
    abstract fun daoDice(): DaoDice
}