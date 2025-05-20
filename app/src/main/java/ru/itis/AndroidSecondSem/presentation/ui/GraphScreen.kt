package ru.itis.AndroidSecondSem.presentation.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itis.AndroidSecondSem.R
import ru.itis.AndroidSecondSem.presentation.viewModel.GraphViewModel

@Composable
fun GraphScreen(viewModel: GraphViewModel) {
    val pointCount by viewModel.pointCount.collectAsState()
    val valuesInput by viewModel.valuesInput.collectAsState()
    val error by viewModel.error.collectAsState()
    val points by viewModel.points.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = pointCount,
            onValueChange = { viewModel.updatePointCount(it) },
            label = { Text(stringResource(R.string.point_count)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = valuesInput,
            onValueChange = { viewModel.updateValuesInput(it) },
            label = { Text(stringResource(R.string.numders)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error!!,
                color = Color.Red,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.buildGraph() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.show_graph))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (points.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.LightGray)
            ) {
                Graph(points = points)
            }
        }
    }
}