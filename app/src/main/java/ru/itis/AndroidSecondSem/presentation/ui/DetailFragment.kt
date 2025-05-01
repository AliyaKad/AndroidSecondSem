package ru.itis.AndroidSecondSem.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.AndroidSecondSem.R
import ru.itis.AndroidSecondSem.databinding.FragmentDetailBinding
import ru.itis.AndroidSecondSem.presentation.viewModel.DetailViewModel
import ru.itis.AndroidSecondSem.Result

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        val currencyCode = arguments?.getString("CURRENCY_CODE") ?: "USD"
        binding.tvCurrencyName.text = getString(R.string.selected_currency, currencyCode)
        binding.btnFetchHistoricalRate.text = getString(R.string.btn_fetch_historical_rate)
        binding.etDateInput.hint = getString(R.string.enter_date_hint)

        binding.btnFetchHistoricalRate.setOnClickListener {
            val date = binding.etDateInput.text.toString()
            if (date.isBlank()) {
                showDialog(getString(R.string.error_dialog_title), getString(R.string.error_invalid_date))
                return@setOnClickListener
            }

            binding.shimmerResult.visibility = View.VISIBLE
            binding.shimmerResult.startShimmer()
            binding.tvHistoricalRate.visibility = View.GONE

            viewModel.fetchHistoricalRate(currencyCode, date)
        }

        viewModel.historicalRate.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    binding.shimmerResult.stopShimmer()
                    binding.shimmerResult.visibility = View.GONE

                    binding.tvHistoricalRate.visibility = View.VISIBLE
                    binding.tvHistoricalRate.text = getString(R.string.historical_rate_placeholder, result.data)                }
                is Result.Failure -> {
                    binding.shimmerResult.stopShimmer()
                    binding.shimmerResult.visibility = View.GONE

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