package id.project.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.project.githubuser.R
import id.project.githubuser.adapter.ListGithubUserAdapter
import id.project.githubuser.data.remote.response.ItemsItem
import id.project.githubuser.databinding.ActivityMainBinding
import id.project.githubuser.theme.ThemePreference
import id.project.githubuser.theme.dataStore
import id.project.githubuser.viewmodel.MainViewModel
import id.project.githubuser.viewmodel.modelfactory.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val preference = ThemePreference.getInstance(application.dataStore)

        val mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(preference)
        )[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithubUser.layoutManager = layoutManager

        with(binding) {
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { itemMenu ->
                when (itemMenu.itemId) {
                    R.id.favorite_list_botton -> {
                        val intentToFavoriteList =
                            Intent(this@MainActivity, FavoriteListActivity::class.java)
                        startActivity(intentToFavoriteList)
                        true
                    }

                    R.id.theme_setting -> {
                        val intentToThemeSetting =
                            Intent(this@MainActivity, SettingActivity::class.java)
                        startActivity(intentToThemeSetting)
                        true
                    }

                    else -> true
                }
            }

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.getListGithubUser(searchBar.text.toString())
                    false
                }
        }

        mainViewModel.listGithubUser.observe(this) { listGithubUser ->
            if (listGithubUser.isEmpty()) {
                binding.errorLogo.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.VISIBLE
                binding.rvGithubUser.visibility = View.GONE
            } else {
                binding.errorLogo.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
                binding.rvGithubUser.visibility = View.VISIBLE
                setListGithubUser(listGithubUser)
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getThemeSetting().observe(this) { isDarkMode: Boolean ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setListGithubUser(listGithubUser: List<ItemsItem>) {
        val adapter = ListGithubUserAdapter()
        adapter.submitList(listGithubUser)
        binding.rvGithubUser.adapter = adapter
        adapter.onItemClickCallBack(object : ListGithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String, avatarUrl: String) {
                val intentToDetailActivity = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetailActivity.putExtra(DetailActivity.DETAIL_GITHUB_USERNAME, username)
                intentToDetailActivity.putExtra(DetailActivity.DETAIL_GITHUB_AVATAR_URL, avatarUrl)
                startActivity(intentToDetailActivity)
            }
        })
    }
}