package ru.itis.AndroidSecondSem.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.itis.AndroidSecondSem.data.model.CurrencyResponse
import ru.itis.AndroidSecondSem.domain.useCase.GetLatestRatesUseCase
import ru.itis.AndroidSecondSem.Result

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLatestRatesUseCase: GetLatestRatesUseCase
) : ViewModel() {

    private val _currencyRates = MutableLiveData<Result<CurrencyResponse>>()
    val currencyRates: LiveData<Result<CurrencyResponse>> = _currencyRates

    fun fetchCurrencyRates() {
        viewModelScope.launch {
            _currencyRates.value = getLatestRatesUseCase()
        }
    }
}