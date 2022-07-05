package asalcedo.com.zemogaapp.ui.add

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import asalcedo.com.zemogaapp.R
import asalcedo.com.zemogaapp.databinding.FragmentAddPostBinding
import asalcedo.com.zemogaapp.domain.model.PostItem
import asalcedo.com.zemogaapp.domain.model.UserItem
import asalcedo.com.zemogaapp.ui.common.viewmodel.PostSharedViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostFragment : Fragment() {

    private lateinit var binding: FragmentAddPostBinding

    private val sharedViewModel by activityViewModels<PostSharedViewModel>()
    private lateinit var postItem: PostItem
    private lateinit var userItem: UserItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInfo()
        addPost()
    }

    private fun setInfo() {
        sharedViewModel.allUsers.observe(viewLifecycleOwner) { usersList ->
            val adapter =
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    usersList.map { user ->
                        user.name
                    })
            binding.atUser.setAdapter(adapter)
        }
    }

    private fun addPost() {
        binding.btnSubmit.setOnClickListener {
            if (validateFields(binding.tilTitle, binding.tilBody)) {
                val title = binding.etTitle.text.toString().trim()
                val body = binding.etBody.text.toString().trim()
                val userIdSelectedId = binding.atUser.selectedItemPosition
                sharedViewModel.allUsers.observe(viewLifecycleOwner) { users ->
                    userItem = users[userIdSelectedId]
                    //The API with any post id that was sent always returns the id_post = 101
                    postItem = PostItem(101, userItem.id ?: 0, title, body)
                    sharedViewModel.addPost(postItem)
                    sharedViewModel.isOkAddPost.observe(viewLifecycleOwner) { isOkAdd ->
                        if (isOkAdd) {
                            cleanFields()
                            hideKeyboard()
                            Toast.makeText(
                                activity,
                                getString(R.string.post_add_sucessfull),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                activity,
                                getString(R.string.failed_add_post),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                }

            }
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
    }

    private fun cleanFields() {
        binding.etTitle.text?.clear()
        binding.etBody.text?.clear()
    }

    private fun validateFields(vararg textFields: TextInputLayout): Boolean {
        var isValid = true

        for (textField in textFields) {
            if (textField.editText?.text.toString().trim().isEmpty()) {
                textField.error = getString(R.string.herlper_required)
                isValid = false
            } else textField.error = null
        }

        if (!isValid) Snackbar.make(
            binding.root,
            R.string.add_post_message_valid,
            Snackbar.LENGTH_SHORT
        ).show()

        return isValid
    }

}