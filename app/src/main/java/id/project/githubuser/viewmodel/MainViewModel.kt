package id.project.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.project.githubuser.data.remote.api.ApiConfig
import id.project.githubuser.data.remote.response.GithubResponse
import id.project.githubuser.data.remote.response.ItemsItem
import id.project.githubuser.theme.ThemePreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preference: ThemePreference) : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _listGithubUser = MutableLiveData<List<ItemsItem>>()
    val listGithubUser: LiveData<List<ItemsItem>> = _listGithubUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getListGithubUser()
    }

    fun getListGithubUser(query: String = "a") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listGithubUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // To set Theme:
    fun getThemeSetting(): LiveData<Boolean> {
        return preference.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            preference.saveThemeSetting(isDarkMode)
        }
    }
}