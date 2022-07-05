package asalcedo.com.zemogaapp.domain.usecase

import asalcedo.com.zemogaapp.data.database.entities.toDatabase
import asalcedo.com.zemogaapp.data.network.PostRepository
import asalcedo.com.zemogaapp.domain.model.UserItem
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: PostRepository) {
    suspend fun getUserById(userId: Int): UserItem = repository.getUserById(userId)

    suspend fun getAllUsers(): List<UserItem> {
        val users = repository.getAllUsersFromApi()
        return if (users.isNotEmpty()) {
            //Delete all users from database
            repository.clearAllUser()
            //Insert the users in database
            repository.insertUsersDatabase(users.map { it.toDatabase() })
            users
        } else {
            repository.getAllUsersFromDatabase()
        }
    }
}