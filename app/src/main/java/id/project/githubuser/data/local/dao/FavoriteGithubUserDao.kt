package id.project.githubuser.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.project.githubuser.data.local.model.FavoriteGithubUser

@Dao
interface FavoriteGithubUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteGithubUser: FavoriteGithubUser)

    @Delete
    suspend fun delete(favoriteGithubUser: FavoriteGithubUser)

    @Query("DELETE from favoritegithubuser")
    suspend fun deleteAll()

    @Query("SELECT * from favoritegithubuser")
    fun getAllFavoriteGithubUser(): LiveData<List<FavoriteGithubUser>>

    @Query("SELECT * from favoritegithubuser WHERE username = :username")
    fun getFavoriteGithubUser(username: String): LiveData<FavoriteGithubUser?>
}