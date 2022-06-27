package id.my.okisulton.mynews_dicoding

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.my.okisulton.mynews_dicoding.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    private val timeSplash = 2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                moveToMain()
            }, timeSplash
        )
    }

    private fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}