package id.my.okisulton.mynews_dicoding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import id.my.okisulton.mynews_dicoding.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = resources.getString(R.string.profile)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onStart() {
        super.onStart()
        setupUi()
    }

    private fun setupUi() {
        binding.includeProfile.apply {
            tvName.text = resources.getString(R.string.name)
            tvEmail.text = resources.getString(R.string.email)

            Glide.with(applicationContext)
                .load(R.drawable.profile_pict)
                .circleCrop()
                .into(ivProfilePict)
        }
    }
}