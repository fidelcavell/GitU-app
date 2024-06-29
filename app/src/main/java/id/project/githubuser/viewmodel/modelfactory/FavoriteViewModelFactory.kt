package id.project.githubuser.viewmodel.modelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.project.githubuser.di.Injection
import id.project.githubuser.repository.FavoriteGithubUserRepository
import id.project.githubuser.viewmodel.DetailViewModel
import id.project.githubuser.viewmodel.FavoriteViewModel

class FavoriteViewModelFactory private constructor(private val favoriteGithubUserRepository: FavoriteGithubUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: FavoriteViewModelFactory? = null
        fun getInstance(context: Context): FavoriteViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteViewModelFactory(Injection.provideFavoriteRepository(context))
            }.also { instance = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(favoriteGithubUserRepository) as T
        }
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteGithubUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}