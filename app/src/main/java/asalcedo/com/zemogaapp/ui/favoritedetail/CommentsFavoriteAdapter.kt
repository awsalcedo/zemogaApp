package asalcedo.com.zemogaapp.ui.favoritedetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import asalcedo.com.zemogaapp.R
import asalcedo.com.zemogaapp.databinding.CommentFavoriteItemBinding
import asalcedo.com.zemogaapp.domain.model.CommentItem

class CommentsFavoriteAdapter(
    private var commentList: List<CommentItem>
) : RecyclerView.Adapter<CommentsFavoriteAdapter.CommentsFavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_favorite_item, parent, false)
        return CommentsFavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsFavoriteViewHolder, position: Int) {
        val comment = commentList[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int = commentList.size

    inner class CommentsFavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CommentFavoriteItemBinding.bind(view)
        fun bind(comment: CommentItem) = with(binding) {
            tvCommentItemTitle.text = comment.body
        }
    }

    fun setCommentList(comments: List<CommentItem>) {
        this.commentList = comments
        notifyDataSetChanged()
    }
}