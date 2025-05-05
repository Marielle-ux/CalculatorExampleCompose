package com.example.calculatorexamplecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorExampleApp()
        }
    }
}

@Composable
fun CalculatorExampleApp(modifier: Modifier = Modifier) {
    val firstValue = remember { mutableStateOf("") }
    val secondValue = remember { mutableStateOf("") }
    val result = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            text = "Result:\n${result.value}"
        )

        OutlinedTextField(
            value = firstValue.value,
            onValueChange = { firstValue.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = { Text("Enter first value") }
        )

        OutlinedTextField(
            value = secondValue.value,
            onValueChange = { secondValue.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            label = { Text("Enter second value") }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val buttonModifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)

            @Composable
            fun RowScope.CalculatorButton(text: String, onClick: () -> Unit) {
                Button(
                    onClick = onClick,
                    modifier = buttonModifier
                ) {
                    Text(text, fontSize = 18.sp)
                }
            }

            Row(Modifier.fillMaxWidth()) {
                CalculatorButton("C") {
                    firstValue.value = ""
                    secondValue.value = ""
                    result.value = ""
                }
                CalculatorButton("+") {
                    result.value = (firstValue.value.toIntOrNull()?.plus(secondValue.value.toIntOrNull() ?: 0)).toString()
                }
                CalculatorButton("-") {
                    result.value = (firstValue.value.toIntOrNull()?.minus(secondValue.value.toIntOrNull() ?: 0)).toString()
                }
            }

            Row(Modifier.fillMaxWidth()) {
                CalculatorButton("*") {
                    result.value = (firstValue.value.toIntOrNull()?.times(secondValue.value.toIntOrNull() ?: 1)).toString()
                }
                CalculatorButton("/") {
                    val n1 = firstValue.value.toDoubleOrNull()
                    val n2 = secondValue.value.toDoubleOrNull()
                    result.value = if (n1 != null && n2 != null && n2 != 0.0) {
                        (n1 / n2).toString()
                    } else {
                        "Ошибка"
                    }
                }
                CalculatorButton("%") {
                    val n1 = firstValue.value.toDoubleOrNull()
                    val n2 = secondValue.value.toDoubleOrNull()
                    result.value = if (n1 != null && n2 != null) {
                        ((n1 * n2) / 100).toString()
                    } else {
                        "Ошибка"
                    }
                }
            }

            Row(Modifier.fillMaxWidth()) {
                CalculatorButton("^") {
                    val base = firstValue.value.toDoubleOrNull()
                    val exponent = secondValue.value.toDoubleOrNull()
                    if (base != null && exponent != null) {
                        val powResult = base.pow(exponent)
                        result.value = if (powResult % 1.0 == 0.0) {
                            powResult.toInt().toString()
                        } else {
                            powResult.toString()
                        }
                    } else {
                        result.value = "Ошибка"
                    }
                }
                Spacer(modifier = Modifier.weight(1f)) // чтобы выровнять по центру
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

