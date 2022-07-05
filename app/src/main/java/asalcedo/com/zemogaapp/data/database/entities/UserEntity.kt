package asalcedo.com.zemogaapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import asalcedo.com.zemogaapp.domain.model.UserItem

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id_table: Int = 0,
    @ColumnInfo(name = "id_user")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "website")
    val website: String
)

fun UserItem.toDatabase() =
    UserEntity(id = id, name = name, email = email, phone = phone, website = website)
