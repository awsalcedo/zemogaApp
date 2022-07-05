package asalcedo.com.zemogaapp.ui.master

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import asalcedo.com.zemogaapp.R
import asalcedo.com.zemogaapp.databinding.FragmentPostListBinding
import asalcedo.com.zemogaapp.domain.model.PostItem
import asalcedo.com.zemogaapp.ui.common.viewmodel.PostSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : Fragment(R.layout.fragment_post_list), OnClickPostListener {

    private lateinit var binding: FragmentPostListBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PostsAdapter
    //private var mainMenu: Menu? = null


    //Access the viewmodel through a delegate
    private val postSharedViewModel by activityViewModels<PostSharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTopMenu(view)
        getInfo()
        setupRecyclerView()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.srRefresh.setOnRefreshListener {
            getInfo()
        }
    }

    private fun setupTopMenu(view: View) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                //mainMenu = menu
                menuInflater.inflate(R.menu.top_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.addPostFragment -> {
                        view.findNavController()
                            .navigate(R.id.action_postListFragment_to_addPostFragment)
                        true
                    }
                    R.id.delete -> {
                        deleteAllPost()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun deleteAllPost() {
        postSharedViewModel.deleteAllPost()
        postSharedViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.pbHome.isVisible = it
        }

        postSharedViewModel.setInfoLiveDataDeleteAllPost()
    }

    private fun getInfo() {
        postSharedViewModel.onCreate()
        postSharedViewModel.postModel.observe(viewLifecycleOwner) { posts ->
            adapter.setPostList(posts)
        }

        postSharedViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.pbHome.isVisible = it
        }

        if (binding.srRefresh.isRefreshing) {
            binding.srRefresh.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        //Set Layout Manger, adapter and optimize
        adapter = PostsAdapter(mutableListOf(), this, postSharedViewModel)
        linearLayoutManager = LinearLayoutManager(activity)
        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.layoutManager = linearLayoutManager
        binding.rvHome.adapter = adapter
    }

    override fun onClick(postItem: PostItem) {
        postSharedViewModel.postSelected(postItem)
        //Launch Detail Fragment
        view?.findNavController()?.navigate(R.id.action_postListFragment_to_postDetailsFragment)
    }

    override fun onFavoritePost(postItem: PostItem) {
        postItem.isFavorite = !postItem.isFavorite
        postSharedViewModel.updatePostFromDatabase(postItem)
        postSharedViewModel.updatePost.observe(viewLifecycleOwner) {
            adapter.updatePost(postItem)
        }
    }

    override fun onDeletePost(postItem: PostItem) {
        postSharedViewModel.deletePost(postItem)
        postSharedViewModel.updatePost.observe(viewLifecycleOwner) {
            adapter.deletePost(postItem)
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item, requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }*/

}