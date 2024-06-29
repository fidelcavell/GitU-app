package id.project.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.project.githubuser.data.remote.api.ApiConfig
import id.project.githubuser.data.remote.response.GithubResponse
import id.project.githubuser.data.remote.response.ItemsItem
import id.project.githubuser.repository.FavoriteGithubUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteViewModel(private val favoriteGithubUserRepository: FavoriteGithubUserRepository) :
    ViewModel() {
    companion object {
        private const val TAG = "favorite_viewmodel"
    }

    private val list = ArrayList<ItemsItem>()
    private val _listGithubUser = MutableLiveData<List<ItemsItem>>()
    val listGithubUser: LiveData<List<ItemsItem>> = _listGithubUser

    fun getListGithubUser(query: String) {
        val client = ApiConfig.getApiService().getListUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.items?.forEach {
                        if (it.login == query && !list.contains(it)) {
                            list.add(it)
                        }
                    }
                    _listGithubUser.value = list
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getAllFavorite() = favoriteGithubUserRepository.getAllFavorite()
}