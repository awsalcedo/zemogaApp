package asalcedo.com.zemogaapp.domain.model

import asalcedo.com.zemogaapp.data.model.CommentModel
import com.google.gson.annotations.SerializedName

data class CommentItem(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)

fun CommentModel.toDomain() = CommentItem(postId, id, name, email, body)
