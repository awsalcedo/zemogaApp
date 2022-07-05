package asalcedo.com.zemogaapp.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import asalcedo.com.zemogaapp.R
import asalcedo.com.zemogaapp.databinding.FavoritePostItemBinding
import asalcedo.com.zemogaapp.domain.model.PostItem
import asalcedo.com.zemogaapp.ui.common.viewmodel.PostFavoriteSharedViewModel
import asalcedo.com.zemogaapp.ui.master.OnClickPostListener

class FavoritePostsAdapter(
    private var postList: MutableList<PostItem>,
    private val listener: OnClickPostListener,
    private var viewModel: PostFavoriteSharedViewModel
) :
    RecyclerView.Adapter<FavoritePostsAdapter.FavoritePostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePostsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_post_item, parent, false)
        return FavoritePostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritePostsViewHolder, position: Int) {
        val post = postList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = postList.size

    inner class FavoritePostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FavoritePostItemBinding.bind(view)

        fun bind(post: PostItem) = with(binding) {
            setListener(post)
            tvFavoriteItemTitle.text = post.title
            favoriteCb.isChecked = post.isFavorite
            deletePostSelected(post)
        }

        private fun deletePostSelected(post: PostItem) {
            binding.imgDelete.setOnClickListener {
                val index = postList.indexOf(post)
                if (index != -1) {
                    viewModel.deletePost(post)
                    postList.removeAt(index)
                    notifyItemRemoved(index)
                }
            }
        }

        private fun setListener(post: PostItem) {
            binding.root.setOnClickListener {
                listener.onClick(post)
            }
            binding.favoriteCb.setOnClickListener {
                listener.onFavoritePost(post)
            }
        }
    }

    fun setFavoritePosts(favoritePosts: List<PostItem>?) {
        this.postList = favoritePosts as MutableList<PostItem>
        notifyDataSetChanged()

    }

    fun updatePost(post: PostItem) {
        //Determine the index of the selected record
        val index = this.postList.indexOf(post)
        if (index != -1) {
            postList[index] = post
            notifyItemChanged(index)
        }
    }

    fun deletePost(postItem: PostItem) {
        //Determine the index of the selected record
        val index = this.postList.indexOf(postItem)
        if (index != -1) {
            postList.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}