package but.app.needice.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="dice")
class DiceEntity {

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

    @ColumnInfo
    lateinit var color: ColorEntity

    @ColumnInfo
    lateinit var range: List<Int>

    @ColumnInfo
    var value: Int = 0

}