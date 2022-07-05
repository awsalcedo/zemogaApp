package asalcedo.com.zemogaapp.domain.usecase

import asalcedo.com.zemogaapp.data.database.dao.PostDao
import asalcedo.com.zemogaapp.data.database.entities.toDatabase
import asalcedo.com.zemogaapp.data.network.PostRepository
import asalcedo.com.zemogaapp.domain.model.PostItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPostUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: PostRepository

    private lateinit var getPostUseCase: GetPostUseCase


    @Before
    fun onBefore() {
        //Initial library of mock
        MockKAnnotations.init(this)
        getPostUseCase = GetPostUseCase(repository)
    }

    @Test
    fun `when the database doesn't anything then get values from api`() = runBlocking {
        coEvery { repository.getAllPostFromDatabase() } returns emptyList()
        getPostUseCase.getAllPost()
        coVerify(exactly = 1) { repository.getAllPostFromApi() }
    }

    @Test
    fun `when the database return something then don't get values from api`() = runBlocking {
        val postList = listOf(
            PostItem(
                1,
                1,
                "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
            )
        )
        coEvery { repository.getAllPostFromDatabase() } returns postList
        val response = getPostUseCase.getAllPost()
        coVerify(exactly = 0) { repository.getAllPostFromApi() }
        assert(postList == response)
    }

    @Test
    fun `when the api get values then insert values from database`() = runBlocking {
        val postList = listOf(
            PostItem(
                1,
                1,
                "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
            )
        )
        coEvery { repository.getAllPostFromApi() } returns postList
        val response = getPostUseCase.getAllPost()
        coVerify(exactly = 1) { repository.clearAllPost() }
        coVerify(exactly = 1) { repository.insertPostDatabase(any()) }
        assert(postList == response)
    }
}