package asalcedo.com.zemogaapp.data.network

import asalcedo.com.zemogaapp.data.model.CommentModel
import asalcedo.com.zemogaapp.data.model.PostModel
import asalcedo.com.zemogaapp.data.model.UserModel
import asalcedo.com.zemogaapp.domain.model.PostItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PostApiClient {

    @GET("posts")
    suspend fun getAllPost(): Response<List<PostModel>>

    @GET("users/{userId}")
    suspend fun getUserById(@Path("userId") userId: Int): Response<UserModel>

    @GET("posts/{postId}/comments")
    suspend fun getCommentsById(@Path("postId") postId: Int): Response<List<CommentModel>>

    @POST("posts")
    suspend fun createPost(@Body postModel: PostModel): Response<PostModel>

    @GET("users")
    suspend fun getAllUsers(): Response<List<UserModel>>

    @DELETE("posts/{postId}")
    suspend fun deletePostById(@Path("postId") postId: Int)
}