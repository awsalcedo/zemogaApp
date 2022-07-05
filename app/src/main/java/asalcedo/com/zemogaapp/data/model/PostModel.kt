package asalcedo.com.zemogaapp.data.model

import asalcedo.com.zemogaapp.domain.model.PostItem
import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("id")
    val id_post: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    var body: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean = false
)

fun PostItem.toModel() = PostModel(id_post, userId, title, body)