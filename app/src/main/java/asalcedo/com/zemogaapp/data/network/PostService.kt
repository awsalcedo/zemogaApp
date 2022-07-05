package asalcedo.com.zemogaapp.data.network

import asalcedo.com.zemogaapp.data.model.CommentModel
import asalcedo.com.zemogaapp.data.model.PostModel
import asalcedo.com.zemogaapp.data.model.UserModel
import asalcedo.com.zemogaapp.data.model.toModel
import asalcedo.com.zemogaapp.domain.model.PostItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PostService @Inject constructor(
    private val postApiClient: PostApiClient
) {

    suspend fun getAllPost(): List<PostModel> {
        return withContext(Dispatchers.IO) {
            val response: Response<List<PostModel>> =
                postApiClient.getAllPost()
            response.body() ?: emptyList()
        }
    }

    suspend fun getUserById(userId: Int): UserModel {
        return withContext(Dispatchers.IO) {
            val response: Response<UserModel> =
                postApiClient.getUserById(userId)
            response.body()!!
        }
    }

    suspend fun getCommentsById(postId: Int): List<CommentModel> {
        return withContext(Dispatchers.IO) {
            val response: Response<List<CommentModel>> =
                postApiClient.getCommentsById(postId)
            response.body() ?: emptyList()
        }
    }

    suspend fun createPost(postItem: PostItem): PostModel {
        return withContext(Dispatchers.IO) {
            val response: Response<PostModel> =
                postApiClient.createPost(postItem.toModel())
            //I create a fake object because the api answers all requests with an id_post 101
            response.body() ?: PostModel(userId = 1, title = "", body = "", id_post = 1)
        }
    }

    suspend fun deletePost(postItem: PostItem) {
        withContext(Dispatchers.IO) {
            postApiClient.deletePostById(postItem.id_post)
        }
    }


    suspend fun getAllUsers(): List<UserModel> {
        return withContext(Dispatchers.IO) {
            val response: Response<List<UserModel>> =
                postApiClient.getAllUsers()
            response.body() ?: emptyList()
        }
    }
}