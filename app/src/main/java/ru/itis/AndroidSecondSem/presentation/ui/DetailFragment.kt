package ru.itis.AndroidSecondSem.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.AndroidSecondSem.R


@AndroidEntryPoint
class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currencyCode = arguments?.getString("CURRENCY_CODE") ?: "USD"

        val navController = findNavController()

        composeView?.setContent {
            DetailScreen(currencyCode = currencyCode, navController = navController)
        }
    }
}