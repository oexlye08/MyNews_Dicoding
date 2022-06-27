package id.my.okisulton.mynews_dicoding

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.my.okisulton.mynews_dicoding.databinding.ActivityDetailBinding
import id.my.okisulton.mynews_dicoding.model.News
import id.my.okisulton.mynews_dicoding.utils.Constants.INTENT_WITH_DATA
import kotlinx.android.synthetic.main.content_detail.*


class DetailActivity : AppCompatActivity() {
    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private var url: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        setupListener()
        getData()
        super.onStart()
    }

    private fun setupListener() {

    }

    private fun getData() {
        val data = intent.getParcelableExtra<News.ArticlesItem>(INTENT_WITH_DATA) as News.ArticlesItem
        val urlImage = data.urlToImage
        val author = data.author
        val source = data.source?.name
        val favorite = data.source?.favorite
        val publish = data.publishedAt
        val title = data.title
        val description = data.description
        val content = data.content
        url = data.url.toString()

        updateUi(urlImage,author,source,favorite,publish,title,description,content,url)
    }

    private fun updateUi(
        urlImage: String?,
        author: String?,
        source: String?,
        favorite: String?,
        publish: String?,
        title: String?,
        description: String?,
        content: String?,
        url: String?
    ) {
        binding.apply {
            tvTitleDetail.text = title
            tvAuthorDetail.text = author
            tvSourceDetail.text = source
            tvPublishDetail.text = publish
            tvDescriptionDetail.text = description
            tvContentDetail.text = content

            collapsingToolbar.title = source

            Glide.with(this@DetailActivity)
                .load(urlImage)
                .centerCrop()
                .into(ivImageNews)

            if (favorite == "1"){
                fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            tvReadMore.setOnClickListener {
                readMore(url)
            }

            btnShareDetail.setOnClickListener {
                shareNews(url)
            }
        }
    }

    private fun readMore(url: String?) {
        if (!url!!.startsWith("http://") && !url.startsWith("https://")){
            this.url = "http://$url";
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun shareNews(urlToShare: String?) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlToShare)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}