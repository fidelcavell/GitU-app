package id.project.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.project.githubuser.adapter.ListGithubUserAdapter
import id.project.githubuser.data.remote.response.ItemsItem
import id.project.githubuser.databinding.ActivityFavoriteListBinding
import id.project.githubuser.viewmodel.FavoriteViewModel
import id.project.githubuser.viewmodel.modelfactory.FavoriteViewModelFactory

class FavoriteListActivity : AppCompatActivity() {
    private var _activityFavoriteListBinding: ActivityFavoriteListBinding? = null
    private val binding get() = _activityFavoriteListBinding!!

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteListBinding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteGithubUser.layoutManager = layoutManager

        favoriteViewModel.getAllFavorite().observe(this) { favoriteList ->
            binding.progressBar.visibility = View.VISIBLE
            binding.errorLogo.visibility = View.GONE
            binding.errorMessage.visibility = View.GONE

            if (favoriteList.isNotEmpty()) {
                favoriteList.forEach { user ->
                    favoriteViewModel.getListGithubUser(user.username)
                }
            } else {
                binding.progressBar.visibility = View.GONE
                binding.errorLogo.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }

        favoriteViewModel.listGithubUser.observe(this) {
            binding.progressBar.visibility = View.GONE
            setListFavoriteGithubUser(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteListBinding = null
    }

    private fun setListFavoriteGithubUser(listGithubUser: List<ItemsItem>) {
        val adapter = ListGithubUserAdapter()
        adapter.submitList(listGithubUser)
        binding.rvFavoriteGithubUser.adapter = adapter
        adapter.onItemClickCallBack(object : ListGithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String, avatarUrl: String) {
                val intentToDetailActivity =
                    Intent(this@FavoriteListActivity, DetailActivity::class.java)
                intentToDetailActivity.putExtra(DetailActivity.DETAIL_GITHUB_USERNAME, username)
                intentToDetailActivity.putExtra(
                    DetailActivity.DETAIL_GITHUB_AVATAR_URL,
                    avatarUrl
                )
                startActivity(intentToDetailActivity)
                finish() // To make DetailActivity Jump to MainActivity
            }
        })
    }
}