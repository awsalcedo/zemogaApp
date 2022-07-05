package asalcedo.com.zemogaapp.domain.model

import asalcedo.com.zemogaapp.data.database.entities.UserEntity
import asalcedo.com.zemogaapp.data.model.UserModel

data class UserItem(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val website: String
)

//Mapper for domain and UI layer
fun UserModel.toDomain() = UserItem(id, name, email, phone, website)
fun UserEntity.toDomain() = UserItem(id, name, email, phone, website)