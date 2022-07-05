package asalcedo.com.zemogaapp.domain.usecase

import asalcedo.com.zemogaapp.data.network.PostRepository
import asalcedo.com.zemogaapp.domain.model.CommentItem
import javax.inject.Inject

class GetCommentUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend fun getCommentsById(postId: Int): List<CommentItem> =
        postRepository.getCommentsById(postId)
}