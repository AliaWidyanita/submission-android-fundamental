package com.dicoding.aplikasigithubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasigithubuser.R
import com.dicoding.aplikasigithubuser.databinding.ActivityMainBinding
import com.dicoding.aplikasigithubuser.ui.adapter.UserAdapter
import com.dicoding.aplikasigithubuser.ui.model.MainModel
import com.dicoding.aplikasigithubuser.ui.model.SettingsModel
import com.dicoding.aplikasigithubuser.ui.model.SettingsModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()

        val layoutManager = LinearLayoutManager(this)
        val mainModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainModel::class.java)
        val adapter = UserAdapter { user ->
            val intent = DetailUserActivity.newIntent(this@MainActivity, user.login)
            startActivity(intent)
        }

        binding.rvListUser.layoutManager = layoutManager
        binding.rvListUser.adapter = adapter

        mainModel.listUser.observe(this) { githubUser ->
            adapter.submitList(githubUser)
        }
        mainModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        mainModel.noUser.observe(this) { noUser ->
            if (noUser) {
                Toast.makeText(this@MainActivity, "Tidak ada user dengan nama tersebut", Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchView.editText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = textView.text.toString()
                mainModel.searchUser(query)
                binding.searchView.hide()
                true
            } else {
                false
            }
        }
        binding.searchView.editText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val query = binding.searchView.editText.text.toString()
                mainModel.searchUser(query)
                binding.searchView.hide()
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu?.findItem(R.id.action_dark_mode)

        val pref = SettingsPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingsModelFactory(pref)).get(
            SettingsModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                menuItem?.setIcon(R.drawable.ic_light_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                menuItem?.setIcon(R.drawable.ic_dark_mode)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.action_dark_mode -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}