package com.dicoding.aplikasigithubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasigithubuser.databinding.FollowingFollowerBinding
import com.dicoding.aplikasigithubuser.ui.adapter.UserAdapter
import com.dicoding.aplikasigithubuser.ui.model.FollowingFollowerModel

class FollowingFollowerFragment : Fragment() {

    private lateinit var binding: FollowingFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FollowingFollowerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val followingFollowerModel = ViewModelProvider(requireActivity()).get(FollowingFollowerModel::class.java)
        val adapter = UserAdapter { user ->
            val intent = DetailUserActivity.newIntent(requireContext(), user.login)
            startActivity(intent)
        }
        val layoutManager = LinearLayoutManager(requireContext())
        val position = requireArguments().getInt(ARG_POSITION)
        val username = requireArguments().getString(ARG_USERNAME) ?: ""

        binding.rvFollow.layoutManager = layoutManager
        binding.rvFollow.adapter = adapter

        if (position == 1) {
            followingFollowerModel.listFollowers.observe(viewLifecycleOwner) { followers ->
                if (followers.isEmpty()) {
                    binding.tvPlaceholder.visibility = View.VISIBLE
                } else {
                    binding.tvPlaceholder.visibility = View.GONE
                    adapter.submitList(followers)
                }
            }
            followingFollowerModel.getFollowers(username)
        } else {
            followingFollowerModel.listFollowing.observe(viewLifecycleOwner) { following ->
                if (following.isEmpty()) {
                    binding.tvPlaceholder.visibility = View.VISIBLE
                } else {
                    binding.tvPlaceholder.visibility = View.GONE
                    adapter.submitList(following)
                }
            }
            followingFollowerModel.getFollowing(username)
        }

        followingFollowerModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.followLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}