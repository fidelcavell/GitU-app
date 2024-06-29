package id.project.githubuser.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import id.project.githubuser.databinding.ActivitySettingBinding
import id.project.githubuser.theme.ThemePreference
import id.project.githubuser.theme.dataStore
import id.project.githubuser.viewmodel.MainViewModel
import id.project.githubuser.viewmodel.modelfactory.MainViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val preference = ThemePreference.getInstance(application.dataStore)

        val mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(preference)
        )[MainViewModel::class.java]

        mainViewModel.getThemeSetting().observe(this) { isDarkMode: Boolean ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}