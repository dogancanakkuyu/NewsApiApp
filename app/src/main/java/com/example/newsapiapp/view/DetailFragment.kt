package com.example.newsapiapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapiapp.R
import com.example.newsapiapp.data.BookmarkRepositoryImpl
import com.example.newsapiapp.data.repo.BookmarkRepository
import com.example.newsapiapp.databinding.FragmentDetailBinding
import com.example.newsapiapp.viewmodel.BookmarkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    private val bookmarkViewModel : BookmarkViewModel by viewModels()
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.apply {
            val url = args.articleContent.url
            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(url)
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackButtonPressed(view.findViewById(R.id.back_button))
        addBookmark()
    }

    private fun onBackButtonPressed(backbutton : ImageView){
        backbutton.setOnClickListener {
            findNavController().navigate(R.id.action_detail_fragment_to_main_fragment)
        }
    }

    private fun addBookmark() {
        binding.bookmarkBtn.setOnClickListener {
            binding.bookmarkBtn.setBackgroundResource(R.drawable.selected_bookmark_icon)
            bookmarkViewModel.addBookmark(args.articleContent)
        }

    }

}