package id.project.githubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.project.githubuser.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var _activitySplashBinding: ActivitySplashBinding? = null
    private val binding get() = _activitySplashBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.splashLogo.animate().setDuration(2000).alpha(1f).withEndAction {
            val intentToMainActivity = Intent(this@SplashActivity, MainActivity::class.java)
            @Suppress("DEPRECATION")
            overridePendingTransition(
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out
            )
            startActivity(intentToMainActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activitySplashBinding = null
    }
}