package id.project.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.project.githubuser.data.local.model.FavoriteGithubUser
import id.project.githubuser.data.remote.api.ApiConfig
import id.project.githubuser.data.remote.response.DetailGithubUserResponse
import id.project.githubuser.repository.FavoriteGithubUserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favoriteGithubUserRepository: FavoriteGithubUserRepository) :
    ViewModel() {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _githubUserDetail = MutableLiveData<DetailGithubUserResponse>()
    val githubUserDetail: LiveData<DetailGithubUserResponse> = _githubUserDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getGithubUserDetail(userName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(userName)
        client.enqueue(object : Callback<DetailGithubUserResponse> {
            override fun onResponse(
                call: Call<DetailGithubUserResponse>,
                response: Response<DetailGithubUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUserDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailGithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFavorite(username: String) = favoriteGithubUserRepository.getFavorite(username)

    fun addFavorite(favoriteGithubUser: FavoriteGithubUser) {
        viewModelScope.launch {
            favoriteGithubUserRepository.addFavorite(favoriteGithubUser)
        }
    }

    fun deleteFavorite(favoriteGithubUser: FavoriteGithubUser) {
        viewModelScope.launch {
            favoriteGithubUserRepository.deleteFavorite(favoriteGithubUser)
        }
    }
}