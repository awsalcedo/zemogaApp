package asalcedo.com.zemogaapp.ui.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import asalcedo.com.zemogaapp.R
import asalcedo.com.zemogaapp.databinding.PostItemBinding
import asalcedo.com.zemogaapp.domain.model.PostItem
import asalcedo.com.zemogaapp.ui.common.viewmodel.PostSharedViewModel

class PostsAdapter(
    private var postList: MutableList<PostItem>,
    private var listener: OnClickPostListener,
    private var viewModel: PostSharedViewModel
) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        //Inflate the view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        //Assign the post that corresponds to that view
        val post = postList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = postList.size

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PostItemBinding.bind(view)

        fun bind(post: PostItem) = with(binding) {
            setListener(post)
            tvHomeItemTitle.text = post.title
            cbFavorite.isChecked = post.isFavorite
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
            binding.cbFavorite.setOnClickListener {
                listener.onFavoritePost(post)
            }
        }
    }

    fun setPostList(posts: List<PostItem>) {
        this.postList = posts as MutableList<PostItem>
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