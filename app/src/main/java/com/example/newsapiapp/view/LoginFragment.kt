package com.example.newsapiapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsapiapp.LoginState
import com.example.newsapiapp.R
import com.example.newsapiapp.databinding.FragmentLoginBinding
import com.example.newsapiapp.extensions.Extensions.findNavControllerSafely
import com.example.newsapiapp.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (loginViewModel.getCurrentUser() != null) {
            findNavController().navigate(R.id.action_loginFragment_to_main_fragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleLogInProcess()
        signUp()
    }
    private fun signUp() {
        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    private fun handleLogInProcess() {
        val email: TextInputEditText = binding.email
        val password: TextInputEditText = binding.password

        binding.logInBtn.setOnClickListener {
            lifecycleScope.launch {
                loginViewModel.logIn(email.text.toString(), password.text.toString())
                loginViewModel.loginViewModel.collect {
                    when (it) {
                        is LoginState.Success -> findNavControllerSafely(R.id.loginFragment)?.navigate(R.id.action_loginFragment_to_main_fragment)
                        is LoginState.Loading -> println("loading")
                        is LoginState.Error -> {
                            binding.errorMsg.text = it.error
                            binding.errorMsg.visibility = View.VISIBLE
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }
}