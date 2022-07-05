package asalcedo.com.zemogaapp.ui.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import asalcedo.com.zemogaapp.domain.model.CommentItem
import asalcedo.com.zemogaapp.domain.model.PostItem
import asalcedo.com.zemogaapp.domain.model.UserItem
import asalcedo.com.zemogaapp.domain.usecase.GetCommentUseCase
import asalcedo.com.zemogaapp.domain.usecase.GetPostUseCase
import asalcedo.com.zemogaapp.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostFavoriteSharedViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getCommentUseCase: GetCommentUseCase
) : ViewModel() {
    //LiveData for Posts
    private val _postModel = MutableLiveData<List<PostItem>>()
    val postModel: LiveData<List<PostItem>> get() = _postModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _updatePost = MutableLiveData<PostItem>()
    val updatePost: LiveData<PostItem> get() = _updatePost

    private val _isOkAddPost = MutableLiveData<Boolean>()
    val isOkAddPost: LiveData<Boolean> get() = _isOkAddPost

    private val _postSelected = MutableLiveData<PostItem>()
    val postSelected: LiveData<PostItem> get() = _postSelected

    //LiveData for Users
    private val _userByIdInfo = MutableLiveData<UserItem>()
    val userByIdInfo: LiveData<UserItem> get() = _userByIdInfo

    //LiveData for Comments
    private val _commentsInfo = MutableLiveData<List<CommentItem>>()
    val commentsInfo: LiveData<List<CommentItem>> get() = _commentsInfo

    fun onCreate() {
        //Call uses case
        viewModelScope.launch {
            //Show progressbar
            _isLoading.postValue(true)
            // Call the usescase
            val result = getPostUseCase.getFavoritesPost()
            if (!result.isNullOrEmpty()) {
                _postModel.postValue(result)
                //Hide progressbar
                _isLoading.postValue(false)
            } else {
                _postModel.postValue(result)
                //Hide progressbar
                _isLoading.postValue(false)
            }
        }
    }

    fun postSelected(postSelected: PostItem) {
        _postSelected.postValue(postSelected)
    }

    fun getDetailInfo(post: PostItem) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getUserUseCase.getUserById(post.userId)
            val resulComments = getCommentUseCase.getCommentsById(post.id_post)
            _commentsInfo.postValue(resulComments)
            _userByIdInfo.postValue(result)
            _isLoading.postValue(false)
        }
    }

    fun updatePostFromDatabase(postItem: PostItem) {
        viewModelScope.launch {
            getPostUseCase.updatePostFromDatabase(postItem)
            _updatePost.postValue(postItem)
            //Refresh list Posts favorites
            //onCreate()
        }
    }

    fun addPost(post: PostItem) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getPostUseCase.createPost(post)
            _isLoading.postValue(false)
            _isOkAddPost.postValue(result)
        }

    }

    fun deletePost(post: PostItem) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            getPostUseCase.deletePostByIdFromApi(post)
            _isLoading.postValue(false)
        }
    }

    fun deleteAllPost() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            getPostUseCase.deleteAllPost()
            _postModel.postValue(emptyList())
            _isLoading.postValue(false)
        }
    }
}