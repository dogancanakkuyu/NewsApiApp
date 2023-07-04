package com.example.newsapiapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapiapp.databinding.FragmentRegisterBinding
import com.example.newsapiapp.viewmodel.AuthenticationViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val authenticationViewModel : AuthenticationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageLoginScreen()
    }

    private fun manageLoginScreen() {
        val email : TextInputEditText = binding.emailTextInput
        val password : TextInputEditText = binding.passwordTextInput
        binding.createAccountBtn.setOnClickListener{
            authenticationViewModel.registerUser(email.text.toString(),password.text.toString())
        }
    }
}