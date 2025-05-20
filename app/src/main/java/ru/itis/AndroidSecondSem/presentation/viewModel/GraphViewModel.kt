package ru.itis.AndroidSecondSem.presentation.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GraphViewModel : ViewModel() {

    private val _pointCount = MutableStateFlow("")
    val pointCount: StateFlow<String> = _pointCount

    private val _valuesInput = MutableStateFlow("")
    val valuesInput: StateFlow<String> = _valuesInput

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _points = MutableStateFlow<List<Float>>(emptyList())
    val points: StateFlow<List<Float>> = _points

    fun updatePointCount(value: String) {
        _pointCount.value = value
        _error.value = null
    }

    fun updateValuesInput(value: String) {
        _valuesInput.value = value
        _error.value = null
    }

    fun buildGraph() {
        try {
            val count = _pointCount.value.toIntOrNull()
            if (count == null || count <= 0) {
                throw IllegalArgumentException("Количество точек должно быть положительным числом")
            }

            val inputValues = _valuesInput.value.split(",").mapNotNull { it.trim().toFloatOrNull() }
            if (inputValues.size != count) {
                throw IllegalArgumentException("Количество значений не совпадает с количеством точек")
            }

            if (inputValues.any { it < 0 }) {
                throw IllegalArgumentException("Значения не должны быть отрицательными")
            }

            _points.value = inputValues
            _error.value = null
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
}