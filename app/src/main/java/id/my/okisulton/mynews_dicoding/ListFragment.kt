package id.my.okisulton.mynews_dicoding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import id.my.okisulton.mynews_dicoding.adapter.NewsAdapter
import id.my.okisulton.mynews_dicoding.databinding.FragmentListBinding
import id.my.okisulton.mynews_dicoding.model.News
import id.my.okisulton.mynews_dicoding.utils.Constants.INTENT_WITH_DATA
import id.my.okisulton.mynews_dicoding.utils.getDataFromAsset

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        getNews()
        setupListener()
    }

    private fun setupListener() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun setupRv() {
        newsAdapter = NewsAdapter(arrayListOf(),
            object : NewsAdapter.OnAdapterListener {
                override fun onClick(result: News.ArticlesItem) {
                    val moveData = Intent(context, DetailActivity::class.java)
                    moveData.putExtra(INTENT_WITH_DATA, result)
                    startActivity(moveData)
                }

                override fun onShareClick(result: News.ArticlesItem) {
                    val urlToShare = result.url
                    shareNews(urlToShare)
                }

                override fun onFavoriteClick(result: News.ArticlesItem) {
                    showToast("<<DUMMY>> -- Berhasil menambahkan ke favorite ${result.title}")
                }
            })

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun getNews() {
        val jsonFile = getDataFromAsset(requireContext(), "news.json")
        val news = Gson().fromJson(jsonFile, News::class.java)
        showResult(news)
    }

    private fun showResult(data: News) {
        val listNews = data.articles
        newsAdapter.setData(listNews)
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

    private fun showToast(message: String) {
        Toast.makeText(activity?.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}