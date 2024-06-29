package id.project.githubuser.repository

import androidx.lifecycle.LiveData
import id.project.githubuser.data.local.dao.FavoriteGithubUserDao
import id.project.githubuser.data.local.model.FavoriteGithubUser

class FavoriteGithubUserRepository private constructor(
    private val favoriteDao: FavoriteGithubUserDao,
) {
    fun getAllFavorite(): LiveData<List<FavoriteGithubUser>> = favoriteDao.getAllFavoriteGithubUser()

    fun getFavorite(username: String): LiveData<FavoriteGithubUser?> {
        return favoriteDao.getFavoriteGithubUser(username)
    }

    suspend fun addFavorite(favoriteGithubUser: FavoriteGithubUser) {
        favoriteDao.insert(favoriteGithubUser)
    }

    suspend fun deleteFavorite(favoriteGithubUser: FavoriteGithubUser) {
        favoriteDao.delete(favoriteGithubUser)
    }

    companion object {
        @Volatile
        private var instance: FavoriteGithubUserRepository? = null
        fun getInstance(favoriteDao: FavoriteGithubUserDao): FavoriteGithubUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteGithubUserRepository(favoriteDao)
            }.also { instance = it }
    }
}