package asalcedo.com.zemogaapp.ui.master

import asalcedo.com.zemogaapp.domain.model.PostItem

interface OnClickPostListener {
    fun onClick(postItem: PostItem)
    fun onFavoritePost(postItem: PostItem)
    fun onDeletePost(postItem: PostItem)
}