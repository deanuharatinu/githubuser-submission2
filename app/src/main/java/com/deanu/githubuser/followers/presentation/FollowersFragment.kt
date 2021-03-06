package com.deanu.githubuser.followers.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deanu.githubuser.databinding.FragmentFollowersBinding
import com.deanu.githubuser.followers.usecase.AdapterFollowersList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowersViewModel by viewModels()

    companion object {
        const val TAG = "FollowersFragment"
        const val USERNAME_KEY = "username"
    }

    fun newInstance(bundle: Bundle): FollowersFragment {
        val fragment = FollowersFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(layoutInflater)

        if (arguments != null) {
            requireArguments().getString(USERNAME_KEY)?.let { username ->
                viewModel.getFollowersList(username)
            }
        }

        viewModel.followersList.observe(viewLifecycleOwner) { followersList ->
            binding.rvFollowersList.adapter = AdapterFollowersList(followersList)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Log.e(TAG, "onCreateView: $errorMessage")
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}