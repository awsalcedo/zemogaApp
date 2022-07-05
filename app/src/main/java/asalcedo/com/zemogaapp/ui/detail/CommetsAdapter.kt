package asalcedo.com.zemogaapp.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import asalcedo.com.zemogaapp.R
import asalcedo.com.zemogaapp.databinding.CommentItemBinding
import asalcedo.com.zemogaapp.domain.model.CommentItem

/****
 * Project: ZemogaApp
 * From: asalcedo.com.zemogaapp.ui.main
 * Created by Alex Salcedo Silva on 24/6/22 at 15:30
 * All rights reserve 2022.
 ***/
class CommetsAdapter(
    private var commentList: List<CommentItem>,
) :
    RecyclerView.Adapter<CommetsAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        //Inflate the view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        //Assign the post that corresponds to that view
        val comment = commentList[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int = commentList.size

    inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CommentItemBinding.bind(view)
        fun bind(commentItem: CommentItem) = with(binding) {
            //Log.e("PostAdapter", post.title)
            tvCommentItemTitle.text = commentItem.body
        }
    }

    fun setCommentList(comments: List<CommentItem>) {
        this.commentList = comments
        notifyDataSetChanged()
    }
}