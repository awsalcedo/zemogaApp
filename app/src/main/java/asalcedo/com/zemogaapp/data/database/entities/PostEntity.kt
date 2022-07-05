package asalcedo.com.zemogaapp.data.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import asalcedo.com.zemogaapp.domain.model.PostItem
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "post_table")
data class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_post")
    val id_post: Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
) : Parcelable

fun PostItem.toDatabase() = PostEntity(id_post, userId, title, body, isFavorite)
