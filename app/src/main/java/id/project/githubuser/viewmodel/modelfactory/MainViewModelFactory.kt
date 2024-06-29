package id.project.githubuser.viewmodel.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.project.githubuser.theme.ThemePreference
import id.project.githubuser.viewmodel.MainViewModel

class MainViewModelFactory(private val preference: ThemePreference) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }
}