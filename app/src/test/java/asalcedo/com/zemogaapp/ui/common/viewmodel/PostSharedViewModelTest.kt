package asalcedo.com.zemogaapp.ui.common.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import asalcedo.com.zemogaapp.domain.model.PostItem
import asalcedo.com.zemogaapp.domain.model.UserItem
import asalcedo.com.zemogaapp.domain.usecase.GetCommentUseCase
import asalcedo.com.zemogaapp.domain.usecase.GetPostUseCase
import asalcedo.com.zemogaapp.domain.usecase.GetUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostSharedViewModelTest {

    @RelaxedMockK
    private lateinit var getPostUseCase: GetPostUseCase

    @RelaxedMockK
    private lateinit var getUserUseCase: GetUserUseCase

    @RelaxedMockK
    private lateinit var getCommentUseCase: GetCommentUseCase

    private lateinit var postSharedViewModel: PostSharedViewModel

    //a rule for testing livedata to avoid boirleplate
    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        postSharedViewModel = PostSharedViewModel(getPostUseCase, getUserUseCase, getCommentUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all Post and Users and set on the livedata`() =
        runTest {
            val postList = listOf(
                PostItem(1, 1, "El paro en Ecuador", "El paro duró 15 días", false),
                PostItem(2, 1, "El paro en Ecuador", "El paro duró 15 días", false)
            )

            val userList = listOf(
                UserItem(1, "sd", "jkj", "354545", "lkjkl"),
                UserItem(2, "sd", "jkj", "354545", "lkjkl")
            )

            coEvery { getPostUseCase.getAllPost() } returns postList
            coEvery { getUserUseCase.getAllUsers() } returns userList
            postSharedViewModel.onCreate()
            assert(postSharedViewModel.postModel.value == postList)
            assert(postSharedViewModel.allUsers.value == userList)
        }

    /*@Test
    fun `if the posts and users are null keep the last value`() = runTest {
        val postList = listOf(
            PostItem(1, 1, "El paro en Ecuador", "El paro duró 15 días", false),
            PostItem(2, 1, "El paro en Ecuador", "El paro duró 15 días", false)
        )

        val userList = listOf(
            UserItem(1, "sd", "jkj", "354545", "lkjkl"),
            UserItem(2, "sd", "jkj", "354545", "lkjkl")
        )

        //TODO ver porque no se puede asignar la lista añ livedata
        //postSharedViewModel.postModel.value = postList
        //postSharedViewModel.allUsers.value == userList

        coEvery { getPostUseCase.getAllPost() } returns emptyList()
        coEvery { getUserUseCase.getAllUsers() } returns emptyList()
        postSharedViewModel.onCreate()

        assert(postSharedViewModel.postModel.value == postList)
        assert(postSharedViewModel.allUsers.value == userList)
    }*/
}