package asalcedo.com.zemogaapp.domain.usecase

import asalcedo.com.zemogaapp.data.network.PostRepository
import asalcedo.com.zemogaapp.domain.model.CommentItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCommentUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: PostRepository

    private lateinit var getCommentUseCase: GetCommentUseCase

    @Before
    fun onBefore() {
        //Initial library of mock
        MockKAnnotations.init(this)
        getCommentUseCase = GetCommentUseCase(repository)
    }

    @Test
    fun `when the api is queried then return the comments by id`() = runBlocking {
        val comments = listOf(
            CommentItem(1, 1, "Hola", "alex@h.com", "fjsjfsdj")
        )
        coEvery { repository.getCommentsById(1) } returns comments
        val response = getCommentUseCase.getCommentsById(1)
        coVerify(exactly = 1) { repository.getCommentsById(any()) }
        assert(comments == response)
    }
}