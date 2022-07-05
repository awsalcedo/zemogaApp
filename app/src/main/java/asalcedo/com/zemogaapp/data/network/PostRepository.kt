package asalcedo.com.zemogaapp.data.network

import asalcedo.com.zemogaapp.data.database.dao.PostDao
import asalcedo.com.zemogaapp.data.database.entities.PostEntity
import asalcedo.com.zemogaapp.data.database.entities.UserEntity
import asalcedo.com.zemogaapp.data.database.entities.toDatabase
import asalcedo.com.zemogaapp.data.model.PostModel
import asalcedo.com.zemogaapp.domain.model.CommentItem
import asalcedo.com.zemogaapp.domain.model.PostItem
import asalcedo.com.zemogaapp.domain.model.UserItem
import asalcedo.com.zemogaapp.domain.model.toDomain
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService,
    private val postDao: PostDao
) {
    //Retrieve all post from remote API
    suspend fun getAllPostFromApi(): List<PostItem> {
        val response = postService.getAllPost()
        return response.map {
            parseBody(it)
            it.toDomain()
        }
    }

    private fun parseBody(it: PostModel) {
        val newBody = it.body.replace("\n", "")
        it.body = newBody
    }

    suspend fun getFavoritesPost(): List<PostItem> {
        val response = postDao.getFavoritesPost()
        return response.map { it.toDomain() }
    }

    //Retrieve all post from database
    suspend fun getAllPostFromDatabase(): List<PostItem> {
        val response = postDao.getAllPosts()
        return response.map { it.toDomain() }
    }

    suspend fun getUserById(userId: Int): UserItem {
        val response = postService.getUserById(userId)
        return response.toDomain()
    }

    suspend fun getCommentsById(postId: Int): List<CommentItem> {
        val response = postService.getCommentsById(postId)
        return response.map { it.toDomain() }
    }

    suspend fun insertPostDatabase(postsList: List<PostEntity?>) {
        postDao.insertAllPosts(postsList as List<PostEntity>)
    }

    suspend fun clearAllPost() {
        postDao.deleteAllPosts()
    }

    suspend fun createPost(postItem: PostItem): PostItem {
        val response = postService.createPost(postItem)
        return response.toDomain()
    }

    suspend fun getAllUsersFromApi(): List<UserItem> {
        val response = postService.getAllUsers()
        return response.map { it.toDomain() }
    }

    suspend fun insertUsersDatabase(userList: List<UserEntity>) {
        postDao.insertAllUsers(userList)
    }

    suspend fun getAllUsersFromDatabase(): List<UserItem> {
        val response = postDao.getAllUsers()
        return response.map { it.toDomain() }
    }

    suspend fun clearAllUser() {
        postDao.deleteAllUsers()
    }

    suspend fun deletePostById(postItem: PostItem) {
        postService.deletePost(postItem)
    }

    suspend fun updatePostFromDatabase(postItem: PostItem) {
        postDao.updatePost(postItem.toDatabase())
    }

    suspend fun deletePostByIdFromDatabase(postItem: PostItem) {
        postDao.deletePostById(postItem.id_post)
    }

    suspend fun insertPostToDatabase(postItem: PostItem) {
        postDao.insertPost(postItem.toDatabase())
    }

    suspend fun getLasPostId(): Int {
        return postDao.getLasPostId()
    }
}