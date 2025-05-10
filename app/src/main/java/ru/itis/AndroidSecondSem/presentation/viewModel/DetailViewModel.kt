package ru.itis.AndroidSecondSem.presentation.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.itis.AndroidSecondSem.Result
import ru.itis.AndroidSecondSem.domain.useCase.GetHistoricalRateUseCase

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getHistoricalRateUseCase: GetHistoricalRateUseCase
) : ViewModel() {

    private val _historicalRate = MutableLiveData<Result<Pair<Double, Boolean>>>()
    val historicalRate: LiveData<Result<Pair<Double, Boolean>>> = _historicalRate

    fun fetchHistoricalRate(currencyCode: String, date: String) {
        viewModelScope.launch {
            try {
                val (rate, isFromCache) = getHistoricalRateUseCase(currencyCode, date)
                _historicalRate.value = Result.Success(Pair(rate, isFromCache))
            } catch (e: Exception) {
                _historicalRate.value = Result.Failure(e)
            }
        }
    }
}