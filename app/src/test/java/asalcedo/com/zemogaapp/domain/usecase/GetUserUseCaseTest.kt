package asalcedo.com.zemogaapp.domain.usecase

import asalcedo.com.zemogaapp.data.network.PostRepository
import asalcedo.com.zemogaapp.domain.model.UserItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUserUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: PostRepository

    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getUserUseCase = GetUserUseCase(repository)
    }

    @Test
    fun `when api get no values then get from database`() = runBlocking {
        coEvery { repository.getAllUsersFromApi() } returns emptyList()
        getUserUseCase.getAllUsers()
        coVerify(exactly = 1) { repository.getAllUsersFromDatabase() }
    }

    @Test
    fun `when api get data then insert into database`() = runBlocking {
        val userList = listOf(
            UserItem(1, "Alex", "alex@h.com", "12345", "www.alex.com")
        )
        coEvery { repository.getAllUsersFromApi() } returns userList
        val response = getUserUseCase.getAllUsers()
        coVerify(exactly = 1) { repository.clearAllUser() }
        coVerify(exactly = 1) { repository.insertUsersDatabase(any()) }
        assert(userList == response)
    }

    @Test
    fun `when the api is queried then return the user by id`() = runBlocking {
        val user = UserItem(1, "Alex", "alex@h.com", "12345", "www.alex.com")
        coEvery { repository.getUserById(any()) } returns user
        val response = getUserUseCase.getUserById(1)
        coVerify(exactly = 1) { repository.getUserById(any()) }
        assert(user == response)
    }
}