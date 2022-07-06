package asalcedo.com.zemogaapp.ui.favorite

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import asalcedo.com.zemogaapp.R
import asalcedo.com.zemogaapp.databinding.FragmentFavoritePostBinding
import asalcedo.com.zemogaapp.domain.model.PostItem
import asalcedo.com.zemogaapp.ui.common.viewmodel.PostFavoriteSharedViewModel
import asalcedo.com.zemogaapp.ui.master.OnClickPostListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePostFragment : Fragment(), OnClickPostListener {

    private lateinit var binding: FragmentFavoritePostBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: FavoritePostsAdapter

    //Access the viewmodel through a delegate
    private val postFavoriteSharedViewModel by activityViewModels<PostFavoriteSharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFavoritePosts()
        setupRecyclerView()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.srRefresh.setOnRefreshListener {
            getFavoritePosts()
        }
    }

    private fun getFavoritePosts() {
        postFavoriteSharedViewModel.onCreate()
        postFavoriteSharedViewModel.postModel.observe(viewLifecycleOwner) { posts ->
            adapter.setFavoritePosts(posts)
        }

        postFavoriteSharedViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.pbFavorite.isVisible = it
        }

        if (binding.srRefresh.isRefreshing) {
            binding.srRefresh.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        adapter = FavoritePostsAdapter(mutableListOf(), this, postFavoriteSharedViewModel)
        linearLayoutManager = LinearLayoutManager(activity)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.layoutManager = linearLayoutManager
        binding.rvFavorite.adapter = adapter
    }

    override fun onClick(postItem: PostItem) {
        postFavoriteSharedViewModel.postSelected(postItem)
        //Launch Detail Fragment
        view?.findNavController()
            ?.navigate(R.id.action_favoritePostFragment_to_favoriteDetailsFragment)
    }

    override fun onFavoritePost(postItem: PostItem) {
        postItem.isFavorite = !postItem.isFavorite
        postFavoriteSharedViewModel.updatePostFromDatabase(postItem)
        postFavoriteSharedViewModel.updatePost.observe(viewLifecycleOwner) {
            adapter.updatePost(postItem)
        }

        postFavoriteSharedViewModel.onCreate()
    }

    override fun onDeletePost(postItem: PostItem) {
        postFavoriteSharedViewModel.deletePost(postItem)
        postFavoriteSharedViewModel.updatePost.observe(viewLifecycleOwner) {
            adapter.deletePost(postItem)
        }
    }

}