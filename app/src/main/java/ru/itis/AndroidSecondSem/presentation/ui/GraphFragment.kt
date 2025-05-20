package ru.itis.AndroidSecondSem.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.fragment.app.viewModels
import ru.itis.AndroidSecondSem.R
import ru.itis.AndroidSecondSem.presentation.viewModel.GraphViewModel

class GraphFragment : BaseFragment(R.layout.fragment_graph) {

    private val viewModel: GraphViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView?.setContent {
            MaterialTheme {
                GraphScreen(viewModel = viewModel)
            }
        }
    }
}