package com.example.newsapiapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsapiapp.R
import com.example.newsapiapp.RegisterState
import com.example.newsapiapp.databinding.FragmentRegisterBinding
import com.example.newsapiapp.viewmodel.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel : RegisterViewModel by viewModels()
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
        manageRegisterScreen()
    }

    private fun manageRegisterScreen() {
        val email : TextInputEditText = binding.emailTextInput
        val password : TextInputEditText = binding.passwordTextInput
        binding.createAccountBtn.setOnClickListener{
            lifecycleScope.launch {
                registerViewModel.registerWithEmailAndPassword(email.text.toString(),password.text.toString())
                registerViewModel.registerFlow.collect{
                    when(it) {
                        is RegisterState.Success -> println(it.msg)
                        is RegisterState.Error -> {
                            binding.errorMsg.text = it.error
                            binding.errorMsg.visibility = View.VISIBLE
                        }
                        else -> RegisterState.Empty
                    }
                }
            }
        }

        binding.logIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}