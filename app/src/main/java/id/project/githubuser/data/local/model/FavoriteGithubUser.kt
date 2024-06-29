package id.project.githubuser.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteGithubUser(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null
) : Parcelable
