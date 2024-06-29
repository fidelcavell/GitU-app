package id.project.githubuser.di

import android.content.Context
import id.project.githubuser.data.local.database.FavoriteRoomDatabase
import id.project.githubuser.data.remote.api.ApiConfig
import id.project.githubuser.repository.FavoriteGithubUserRepository

object Injection {
    fun provideFavoriteRepository(context: Context): FavoriteGithubUserRepository {
        val favoriteDatabase = FavoriteRoomDatabase.getDatabase(context)
        val favoriteDao = favoriteDatabase.favoriteDao()

        return FavoriteGithubUserRepository.getInstance(favoriteDao)
    }
}