package ru.itis.AndroidSecondSem.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itis.AndroidSecondSem.R
import ru.itis.AndroidSecondSem.presentation.viewModel.DetailViewModel
import ru.itis.AndroidSecondSem.Result
import androidx.compose.material3.TextField
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController

@Composable
fun DetailScreen(
    currencyCode: String,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val historicalRateState by viewModel.historicalRate.observeAsState(Result.Loading)
    val context = LocalContext.current

    var dateInput by remember { mutableStateOf("") }

    var errorDialogVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var isFetching by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.selected_currency, currencyCode),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = dateInput,
            onValueChange = { dateInput = it },
            label = { Text(stringResource(id = R.string.enter_date_hint)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (dateInput.isBlank()) {
                errorMessage = context.getString(R.string.error_invalid_date)
                errorDialogVisible = true
            } else {
                isFetching = true
                viewModel.fetchHistoricalRate(currencyCode, dateInput)
            }
        }) {
            Text(text = stringResource(id = R.string.btn_fetch_historical_rate))
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isFetching && historicalRateState is Result.Loading -> {
                ShimmerEffect()
            }
            historicalRateState is Result.Success<*> -> {
                isFetching = false
                val (rate, isFromCache) = (historicalRateState as Result.Success<Pair<Double, Boolean>>).data
                Text(
                    text = stringResource(id = R.string.historical_rate_placeholder, rate),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
                LaunchedEffect(isFromCache) {
                    Toast.makeText(
                        context,
                        if (isFromCache) context.getString(R.string.cache_source) else context.getString(R.string.api_source),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            historicalRateState is Result.Failure -> {
                isFetching = false
                val exceptionMessage = (historicalRateState as Result.Failure).exception.message
                errorMessage = context.getString(R.string.error_server_message, exceptionMessage)
                errorDialogVisible = true
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.detail_screen_description),
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(onClick = {
            navController.navigate(R.id.graphFragment)
        }) {
            Text(stringResource(id = R.string.go_to_graph))
        }
    }

    if (errorDialogVisible) {
        AlertDialog(
            onDismissRequest = { errorDialogVisible = false },
            title = { Text(text = stringResource(id = R.string.error_dialog_title)) },
            text = { Text(text = errorMessage) },
            confirmButton = {
                Button(onClick = { errorDialogVisible = false }) {
                    Text(text = stringResource(id = R.string.ok_button))
                }
            }
        )
    }
}