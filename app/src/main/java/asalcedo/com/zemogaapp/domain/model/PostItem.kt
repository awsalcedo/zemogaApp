package asalcedo.com.zemogaapp.domain.model

import asalcedo.com.zemogaapp.data.database.entities.PostEntity
import asalcedo.com.zemogaapp.data.model.PostModel

data class PostItem(
    var id_post: Int,
    var userId: Int,
    var title: String,
    var body: String,
    var isFavorite: Boolean = false,
    var selected: Boolean? = false
)

//Mappers for domain and UI layer
fun PostModel.toDomain() = PostItem(id_post, userId, title, body, isFavorite)
fun PostEntity.toDomain() = PostItem(id_post, userId, title, body, isFavorite)
