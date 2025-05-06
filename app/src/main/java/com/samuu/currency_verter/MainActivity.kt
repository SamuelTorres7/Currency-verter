package com.samuu.currency_verter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samuu.currency_verter.ui.theme.CurrencyverterTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            CurrencyverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

//El panel principal de la aplicación //
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    // Configura el idioma de la aplicación según el dispositivo //
    val lang : String = Locale.getDefault().language
    val welcome : String = when (lang) {
        "es" -> stringResource(R.string.es_welcome)
        else -> stringResource(R.string.en_welcome)
    }
    val convertTo : String = when(lang) {
        "es" -> stringResource(R.string.es_convert)
        else -> stringResource(R.string.en_convert)
    }
    val quantity : String = when(lang) {
        "es" -> stringResource(R.string.es_quantity)
        else -> stringResource(R.string.en_quantity)
    }
    val currencyDesc : String = when(lang) {
        "es" -> stringResource(R.string.es_currency_desc)
        else -> stringResource(R.string.en_currency_desc)
    }

    // Estado para almacenar la moneda seleccionada y la cantidad ingresada //
    var currency by remember { mutableStateOf("MXN") }
    var amount by remember { mutableStateOf("") }

    // Columna principal //
    Column(modifier = modifier.fillMaxSize().background(Color(39, 0, 93)),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painterResource(R.drawable.convertidor),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.Center
        )
        Spacer(Modifier.height(16.dp))
        // Label de bienvenida //
        Text(
            text = welcome,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .background(Color(148, 0, 255),shape = RoundedCornerShape(16.dp))
                .padding(20.dp)
        )
        Spacer(Modifier.height(40.dp))
        // Campo de entrada para la cantidad en pesos mexicanos//
        TextField(
            value = amount,
            onValueChange = { newValue ->
                    amount = newValue
            },
            label = { Text(
                text = quantity
            ) },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.money),
                    contentDescription = currencyDesc
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(Modifier.height(16.dp))
        // Composable donde se muestran los resultados de la conversión //
        CurrencyConverter(currency, amount.toDoubleOrNull() ?: 0.0)
        Spacer(Modifier.height(16.dp))
        // Introducción al menu de monedas //
        Text(
            text = convertTo,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 30.dp),
            color = Color.White
        )
        // Fila de RadioButtons para seleccionar la moneda destino //
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                text = "USD",
                fontSize = 15.sp,
                color = Color.White
            )
            RadioButton(
                onClick = { currency = "USD" },
                selected = currency == "USD",
                modifier = Modifier.padding(12.dp)
            )
            Text(
                text = "EUR",
                fontSize = 15.sp,
                color = Color.White
            )
            RadioButton(
                onClick = { currency = "EUR" },
                selected = currency == "EUR",
                modifier = Modifier.padding(12.dp)
            )
            Text(
                text = "JPY",
                fontSize = 15.sp,
                color = Color.White
            )
            RadioButton(
                onClick = { currency = "JPY" },
                selected = currency == "JPY",
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
// Label que muestra el resultado de la conversión //
@Composable
fun CurrencyConverter(currency: String, amount: Double) {
    var currencySymbol = when (currency) {
        "USD" -> "$"
        "EUR" -> "€"
        "JPY" -> "¥"
        else -> "$"
    }
    var finalAmount = when (currency) {
        "USD" -> Math.ceil(amount * 0.05714)
        "EUR" -> Math.ceil(amount * 0.05208)
        "JPY" -> Math.ceil(amount * 8.33333)
        else -> amount
    }
    Text(
        text = "$currency : $currencySymbol $finalAmount",
        fontSize = 25.sp,
        color = Color.Black,
        modifier = Modifier.background(Color(212, 190, 228),shape = RoundedCornerShape(16.dp))
            .padding(20.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CurrencyverterTheme {
        MainScreen(modifier = Modifier)
    }
}