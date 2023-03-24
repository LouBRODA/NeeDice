package but.app.needice.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="color")
class ColorEntity {

    @PrimaryKey(autoGenerate = false)
    val id: String = ""

    @ColumnInfo
    val name: String = ""
}