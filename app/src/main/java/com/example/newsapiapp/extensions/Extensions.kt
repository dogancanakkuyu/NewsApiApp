package com.example.newsapiapp.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

object Extensions {
    fun Fragment.findNavControllerSafely(id: Int): NavController? {
        return if (findNavController().currentDestination?.id == id) {
            findNavController()
        } else {
            null
        }
    }
}