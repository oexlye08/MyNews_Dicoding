package id.my.okisulton.mynews_dicoding.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.my.okisulton.mynews_dicoding.R
import id.my.okisulton.mynews_dicoding.databinding.ListNewsBinding
import id.my.okisulton.mynews_dicoding.model.News
import java.text.SimpleDateFormat
import java.util.*

/**
 *Created by osalimi on 24-06-2022.
 **/
class NewsAdapter(
    private val listNews: ArrayList<News.ArticlesItem>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    interface OnAdapterListener {
        fun onClick(result: News.ArticlesItem)
        fun onShareClick(result: News.ArticlesItem)
        fun onFavoriteClick(result: News.ArticlesItem)
    }

    class ViewHolder(val binding: ListNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listNews[position]

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val date: Date = inputFormat.parse(data.publishedAt.toString())!!
        val formattedDate: String = outputFormat.format(date)

        val url = data.urlToImage
        val title = data.title
        val source = data.source?.name
        val favorite = data.source?.favorite

        holder.binding.apply {
            tvTitleNews.text = title
            tvSource.text = source
            tvPublishedAt.text = formattedDate
            if (favorite == "1") {
                btnFavorite.setIconResource(R.drawable.ic_baseline_favorite_24)
            } else {
                btnFavorite.setIconResource(R.drawable.ic_baseline_favorite_border_24)
            }

            btnFavorite.setOnClickListener {
                listener.onFavoriteClick(data)
            }

            btnShare.setOnClickListener {
                listener.onShareClick(data)
            }

            holder.binding.root.setOnClickListener {
                listener.onClick(data)
            }

            Glide.with(holder.binding.root)
                .load(url)
                .centerCrop()
                .into(ivImageNews)
        }
    }

    override fun getItemCount() = listNews.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<News.ArticlesItem>) {
        listNews.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        listNews.clear()
        notifyDataSetChanged()
    }
}