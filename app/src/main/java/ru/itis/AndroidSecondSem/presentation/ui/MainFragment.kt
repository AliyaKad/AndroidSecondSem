package ru.itis.AndroidSecondSem.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.AndroidSecondSem.R
import ru.itis.AndroidSecondSem.databinding.FragmentMainBinding
import ru.itis.AndroidSecondSem.presentation.viewModel.MainViewModel
import ru.itis.AndroidSecondSem.Result
import ru.itis.AndroidSecondSem.presentation.adapter.CurrencyAdapter

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        binding.fragmentName.text = getString(R.string.main_screen_title)

        val adapter = CurrencyAdapter { currencyCode ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(currencyCode)
            findNavController().navigate(action)
        }

        binding.rvCurrencyList.adapter = adapter
        binding.rvCurrencyList.layoutManager = LinearLayoutManager(requireContext())

        binding.btnFetchRates.text = getString(R.string.btn_fetch_rates)

        binding.btnFetchRates.setOnClickListener {
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmer()
            binding.rvCurrencyList.visibility = View.GONE
            viewModel.fetchCurrencyRates()
        }

        viewModel.currencyRates.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE

                    binding.rvCurrencyList.visibility = View.VISIBLE

                    val rates = result.data.rates.keys.toList()
                    adapter.submitList(rates)
                }
                is Result.Failure -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    showDialog(
                        getString(R.string.error_dialog_title),
                        getString(R.string.error_server_message, result.exception.message)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialog(title: String, message: String) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok_button)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }
}