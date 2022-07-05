package asalcedo.com.zemogaapp.ui.favoritedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import asalcedo.com.zemogaapp.databinding.FragmentFavoriteDetailsBinding
import asalcedo.com.zemogaapp.ui.common.viewmodel.PostFavoriteSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteDetailsFragment : Fragment() {

    private val postFavoriteSharedViewModel by activityViewModels<PostFavoriteSharedViewModel>()
    private lateinit var binding: FragmentFavoriteDetailsBinding
    private lateinit var adapter: CommentsFavoriteAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfoDetail()
        setupRecyclerView()
    }

    private fun getInfoDetail() {
        postFavoriteSharedViewModel.postSelected.observe(viewLifecycleOwner) { post ->
            postFavoriteSharedViewModel.getDetailInfo(post)
            binding.tvDescription.text = post.body
            binding.cbFavorite.isChecked = post.isFavorite
        }

        postFavoriteSharedViewModel.commentsInfo.observe(viewLifecycleOwner) { comments ->
            adapter.setCommentList(comments)
        }

        postFavoriteSharedViewModel.userByIdInfo.observe(viewLifecycleOwner) { user ->
            binding.tvName.text = user.name
            binding.tvEmail.text = user.email
            binding.tvPhone.text = user.phone
            binding.tvWebsite.text = user.website
        }
    }

    private fun setupRecyclerView() {
        //Set Layout Manger, adapter and optimize
        adapter = CommentsFavoriteAdapter(emptyList())
        linearLayoutManager = LinearLayoutManager(activity)
        binding.rvComments.setHasFixedSize(true)
        binding.rvComments.layoutManager = linearLayoutManager
        binding.rvComments.adapter = adapter
    }
}