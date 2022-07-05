package asalcedo.com.zemogaapp.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("website")
    val website: String
)
