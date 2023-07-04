package com.example.newsapiapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiapp.adapter.CategoriesAdapter
import com.example.newsapiapp.adapter.RecyclerViewAdapter
import com.example.newsapiapp.data.BookmarkRepository
import com.example.newsapiapp.databinding.FragmentMainBinding
import com.example.newsapiapp.extensions.setDivider
import com.example.newsapiapp.viewmodel.AuthenticationViewModel
import com.example.newsapiapp.viewmodel.NewsViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragment : Fragment(), CategoriesAdapter.ClickListener {

    private lateinit var binding : FragmentMainBinding
    private lateinit var listAdapter: RecyclerViewAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private val newsViewModel: NewsViewModel by viewModels()
    private val authenticationViewModel : AuthenticationViewModel by viewModels()
    private var selectedItem : Int = 0

    private val categories : List<String> = listOf("general","business","entertainment","health","science","sports","technology")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesAdapter = CategoriesAdapter(categories,this)
        binding.recyclerViewCategories.adapter = categoriesAdapter

        sendNewRequest(0)
        println(authenticationViewModel.getUser().uid)
        logOut()
     }
    fun logOut() {
        binding.logOutBtn.setOnClickListener {
            authenticationViewModel.logOutFromAccount()
            findNavController().navigate(R.id.action_main_fragment_to_loginFragment)
        }
    }

    fun sendNewRequest(item : Int){
        newsViewModel.articles.observe(viewLifecycleOwner) {

            if (it != null) {
                println(it)
                listAdapter = it.articles?.let { it1 -> RecyclerViewAdapter(it1) }!!
                binding.recyclerView.adapter = listAdapter
            }
        }
        newsViewModel.getArticlesFromApi(categories[item])
    }

    override fun onClickListener(item: Int) {
        sendNewRequest(item)
    }

    override fun getSelectedItem() : Int {
        return selectedItem
    }

    override fun setSelectedItem(item: Int) {
        selectedItem = item
    }

}