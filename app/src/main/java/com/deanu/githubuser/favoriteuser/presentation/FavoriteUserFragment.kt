package com.deanu.githubuser.favoriteuser.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.deanu.githubuser.R
import com.deanu.githubuser.databinding.FragmentFavoriteUserBinding
import com.deanu.githubuser.detailuser.presentation.UserDetailFragment.Companion.FAVORITE
import com.deanu.githubuser.favoriteuser.usecase.AdapterFavoriteUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteUserFragment : Fragment() {
    private var _binding: FragmentFavoriteUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteUserBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.favorite_user_title)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val view = binding.root

        val adapter =
            AdapterFavoriteUser({ userId: Int -> viewModel.onUserDelete(userId) },
                { username: String -> viewModel.onCardClicked(username) })
        binding.rvFavoriteUser.adapter = adapter

        viewModel.getFavoriteUsers()

        viewModel.userList.observe(viewLifecycleOwner) { favoriteUserList ->
            favoriteUserList?.let { adapter.submitList(favoriteUserList) }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressCircular.visibility = View.VISIBLE
            } else {
                binding.progressCircular.visibility = View.GONE
            }
        }

        viewModel.userIdToDelete.observe(viewLifecycleOwner) { userIdToDelete ->
            viewModel.deleteFavoriteUser(userIdToDelete)
            viewModel.getFavoriteUsers()
        }

        viewModel.navigateToUserDetail.observe(viewLifecycleOwner) { username ->
            username?.let {
                val action =
                    FavoriteUserFragmentDirections.actionFavoriteUserFragmentToUserDetailFragment(it, FAVORITE)
                view.findNavController().navigate(action)
                viewModel.onCardNavigated()
            }
        }

        return view
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}