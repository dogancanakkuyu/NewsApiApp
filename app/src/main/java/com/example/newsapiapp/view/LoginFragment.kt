package com.example.newsapiapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapiapp.R
import com.example.newsapiapp.databinding.FragmentLoginBinding
import com.example.newsapiapp.viewmodel.AuthenticationViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val authenticationViewModel : AuthenticationViewModel by viewModels()

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = authenticationViewModel.getUser()
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_main_fragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleLogInProcess()
    }

    fun handleLogInProcess() {
        val email : TextInputEditText = binding.email
        val password : TextInputEditText = binding.password

        binding.logInBtn.setOnClickListener {
            authenticationViewModel.logInUser(email.text.toString(), password.text.toString())
            if(authenticationViewModel.isUserExists()) findNavController().navigate(R.id.action_loginFragment_to_main_fragment)
        }
    }
}