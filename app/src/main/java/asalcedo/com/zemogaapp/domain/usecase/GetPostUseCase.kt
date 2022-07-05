package asalcedo.com.zemogaapp.domain.usecase

import asalcedo.com.zemogaapp.data.database.entities.toDatabase
import asalcedo.com.zemogaapp.data.network.PostRepository
import asalcedo.com.zemogaapp.domain.model.PostItem
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val repository: PostRepository,
) {
    suspend fun getAllPost(): List<PostItem> {
        val responseDatabase = repository.getAllPostFromDatabase()
        return if (responseDatabase.isNullOrEmpty()) {
            val posts = repository.getAllPostFromApi()
            //Delete all posts from database
            repository.clearAllPost()
            //Insert the posts in database
            repository.insertPostDatabase(posts.map { it.toDatabase() })
            posts
        } else {
            repository.getAllPostFromDatabase()
        }
    }

    suspend fun getFavoritesPost(): List<PostItem> {
        return repository.getFavoritesPost()
    }

    suspend fun updatePostFromDatabase(postItem: PostItem) {
        repository.updatePostFromDatabase(postItem)
    }

    suspend fun createPost(postItem: PostItem): Boolean {
        //Create the new post in the API then query the maximum id in the database
        // to insert it because the API always returns the same id as the post
        var isOk = false
        val newPost = repository.createPost(postItem)
        if (newPost != null) {
            val maxId = repository.getLasPostId()
            postItem.id_post = maxId + 1
            repository.insertPostToDatabase(postItem)
            isOk = true
        }
        return isOk
    }

    suspend fun deletePostByIdFromApi(postItem: PostItem) {
        //Resource will not be really updated on the server but it will be faked as if
        repository.deletePostById(postItem)
        repository.deletePostByIdFromDatabase(postItem)
    }

    suspend fun deleteAllPost() {
        repository.clearAllPost()
    }
}