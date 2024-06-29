package id.project.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import id.project.githubuser.R
import id.project.githubuser.adapter.SectionPagerAdapter
import id.project.githubuser.data.local.model.FavoriteGithubUser
import id.project.githubuser.data.remote.response.DetailGithubUserResponse
import id.project.githubuser.databinding.ActivityDetailBinding
import id.project.githubuser.viewmodel.DetailViewModel
import id.project.githubuser.viewmodel.modelfactory.FavoriteViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        FavoriteViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val githubUsername = intent.getStringExtra(DETAIL_GITHUB_USERNAME)
        val githubAvatarUrl = intent.getStringExtra(DETAIL_GITHUB_AVATAR_URL)
        val favoriteGithubUser = FavoriteGithubUser(githubUsername.toString(), githubAvatarUrl)

        detailViewModel.getGithubUserDetail(githubUsername.toString())
        detailViewModel.getFavorite(githubUsername.toString())

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.githubUsername = githubUsername.toString()
        binding.viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(binding.tabIndicator, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.githubUserDetail.observe(this) { detailGithubUser ->
            setDetailGithubUser(detailGithubUser)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.getFavorite(githubUsername.toString()).observe(this) { isFavorite ->
            if (isFavorite != null) {
                setFAB(true, favoriteGithubUser)
            } else {
                setFAB(false, favoriteGithubUser)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setDetailGithubUser(githubUser: DetailGithubUserResponse) {
        Glide.with(binding.detailProfilePicture.context)
            .load(githubUser.avatarUrl)
            .circleCrop()
            .into(binding.detailProfilePicture)
        binding.detailUsername.text = githubUser.login
        binding.detailName.text = githubUser.name
        binding.detailFollowers.text = resources.getString(R.string.followers, githubUser.followers)
        binding.detailFollowing.text = resources.getString(R.string.following, githubUser.following)
    }

    private fun setFAB(isFavorite: Boolean, favoriteGithubUser: FavoriteGithubUser) {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
            binding.fabFavorite.setOnClickListener {
                detailViewModel.deleteFavorite(favoriteGithubUser)
                Snackbar.make(
                    binding.detailActivity,
                    "Deleted from Favorite List",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            binding.fabFavorite.setOnClickListener {
                detailViewModel.addFavorite(favoriteGithubUser)
                Snackbar.make(
                    binding.detailActivity,
                    "Added to Favorite List",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        const val DETAIL_GITHUB_USERNAME = "detail_github_username"
        const val DETAIL_GITHUB_AVATAR_URL = "detail_github_avatar_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_name_1,
            R.string.tab_name_2
        )
    }
}