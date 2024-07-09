package com.dicoding.aplikasigithubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasigithubuser.databinding.ActivityFavoriteBinding
import com.dicoding.aplikasigithubuser.ui.adapter.FavoriteAdapter
import com.dicoding.aplikasigithubuser.ui.model.FavoriteModel
import com.dicoding.aplikasigithubuser.ui.model.FavoriteModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var adapter: FavoriteAdapter

    private lateinit var favoriteModel: FavoriteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.listFavorite.layoutManager = LinearLayoutManager(this)
        adapter = FavoriteAdapter { favoriteUser ->
            val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, favoriteUser.username)
            startActivity(intent)
        }
        binding.listFavorite.adapter = adapter

        val factory = FavoriteModelFactory.getInstance(application)
        favoriteModel = ViewModelProvider(this, factory)[FavoriteModel::class.java]

        favoriteModel.getAllFavorite().observe(this) { favoriteUsers ->
            adapter.submitList(favoriteUsers)
        }
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val EXTRA_USERNAME = "extra_username"
    }
}