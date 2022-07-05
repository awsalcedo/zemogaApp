package asalcedo.com.zemogaapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import asalcedo.com.zemogaapp.databinding.FragmentPostDetailsBinding
import asalcedo.com.zemogaapp.ui.common.viewmodel.PostSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    private val postSharedViewModel by activityViewModels<PostSharedViewModel>()
    private lateinit var binding: FragmentPostDetailsBinding
    private lateinit var adapter: CommetsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfoDetail()
        setupRecyclerView()
    }

    private fun getInfoDetail() {
        postSharedViewModel.postSelected.observe(viewLifecycleOwner) { post ->
            postSharedViewModel.getDetailInfo(post)
            binding.tvDescription.text = post.body.toString()
            binding.cbFavorite.isChecked = post.isFavorite
        }

        postSharedViewModel.commentsInfo.observe(viewLifecycleOwner) { comments ->
            adapter.setCommentList(comments)
        }

        postSharedViewModel.userByIdInfo.observe(viewLifecycleOwner) { user ->
            binding.tvName.text = user.name.toString()
            binding.tvEmail.text = user.email.toString()
            binding.tvPhone.text = user.phone.toString()
            binding.tvWebsite.text = user.website.toString()
        }
    }

    private fun setupRecyclerView() {
        //Set Layout Manger, adapter and optimize
        adapter = CommetsAdapter(emptyList())
        linearLayoutManager = LinearLayoutManager(activity)
        binding.rvComments.setHasFixedSize(true)
        binding.rvComments.layoutManager = linearLayoutManager
        binding.rvComments.adapter = adapter
    }
}