package com.dicoding.aplikasigithubuser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.aplikasigithubuser.R
import com.dicoding.aplikasigithubuser.data.DetailUser
import com.dicoding.aplikasigithubuser.data.local.UserFavorite
import com.dicoding.aplikasigithubuser.databinding.DetailUserBinding
import com.dicoding.aplikasigithubuser.ui.adapter.PageAdapter
import com.dicoding.aplikasigithubuser.ui.model.DetailUserModel
import com.dicoding.aplikasigithubuser.ui.model.FavoriteAddModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: DetailUserBinding

    private lateinit var detailUserModel: DetailUserModel

    private lateinit var favoriteAddModel: FavoriteAddModel

    private fun showLoading(isLoading: Boolean) {
        binding.detailLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        favoriteAddModel = ViewModelProvider(this).get(FavoriteAddModel::class.java)

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        val pageAdapter = PageAdapter(this)

        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)

        detailUserModel = ViewModelProvider(this).get(DetailUserModel::class.java)
        detailUserModel.getDetailUser(username)
        detailUserModel.detailUser.observe(this) { detailUser ->
            if (detailUser != null) {
                setDetailUser(detailUser)
            }
            checkIsUserFavorite(username)
        }
        detailUserModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        pageAdapter.username = username
        binding.viewPager.adapter = pageAdapter
        binding.tabs.apply {
            TabLayoutMediator(this, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        binding.fabFavorite.setOnClickListener {
            addToFavorite()
        }
    }

    private fun setDetailUser(detailUser: DetailUser) {
        binding.apply {
            tvDetailName.text = detailUser.name ?: ""
            tvUsername.text = detailUser.login ?: ""
            tvUserDetailFollowers.text = detailUser.followers.toString()
            tvUserDetailFollowing.text = detailUser.following.toString()

            Glide.with(this@DetailUserActivity)
                .load(detailUser.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.ic_person)
                .into(ivUserDetail)
        }
    }

    private fun checkIsUserFavorite(username: String) {
        favoriteAddModel.checkIsUserFavorite(username).observe(this) { isFavorite ->
            if (isFavorite) {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                binding.fabFavorite.setOnClickListener {
                    deleteFromFavorite()
                }
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                binding.fabFavorite.setOnClickListener {
                    addToFavorite()
                }
            }
        }
    }

    private fun addToFavorite() {
        val username = detailUserModel.detailUser.value?.login
        val avatarUrl = detailUserModel.detailUser.value?.avatarUrl

        if (username != null && avatarUrl != null) {
            val favorite = UserFavorite(username = username, avatarUrl = avatarUrl)
            favoriteAddModel.insert(favorite)

            Toast.makeText(this, "Successfully added user to favorites", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to add user to favorites", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteFromFavorite() {
        val username = detailUserModel.detailUser.value?.login
        val avatarUrl = detailUserModel.detailUser.value?.avatarUrl

        if (username != null && avatarUrl != null) {
            val favoriteUser = UserFavorite(username = username, avatarUrl = avatarUrl)
            favoriteAddModel.delete(favoriteUser)

            Toast.makeText(this, "Successfully deleted user from favorites", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, "Failed to delete user from favorites", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

        private const val EXTRA_USERNAME = "extra_username"

        fun newIntent(context: Context, username: String): Intent {
            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, username)
            return intent
        }
    }
}