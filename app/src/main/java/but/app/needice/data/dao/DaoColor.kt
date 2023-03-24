package but.app.needice.data.dao

import androidx.room.Dao
import androidx.room.Insert
import but.app.needice.data.entity.ColorEntity

@Dao
interface DaoColor {

    @Insert
    fun insertColor(c: ColorEntity)
}