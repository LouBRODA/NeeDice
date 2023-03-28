package but.app.needice.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import but.app.needice.entity.ColorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoColor {

    @Insert
    suspend fun insertColor(c: ColorEntity)

    @Query("SELECT * FROM color")
    fun getColors():Flow<List<ColorEntity>>
}