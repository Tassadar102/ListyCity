package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class CityRepository {
    //
    private val _cities = mutableStateListOf(
        "Minas Tirith", "Minas Morgul", "The Shire",
        "Baradur", "Osgiliath", "Lothlorien"
    )

    val cities: List<String> get() = _cities

    fun addCity(city: String) {
        //
        _cities.add(city)
    }

    fun removeCity(city: String) {
        /* This method removes a city from the array.
         * city: is a String matching a city already in the array.
         */
        if (_cities.isNotEmpty()) {
            _cities.remove(city)
        }
    }
}


@Composable
fun CityListScreen(
    cities: List<String>,
    modifier: Modifier = Modifier,
    onAddCity: (String) -> Unit
) {
    var newCityName by remember {mutableStateOf("") }
    var showNewCity by remember {mutableStateOf(false)}

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {
            // City adder button.
            Button(
                onClick = {
                    showNewCity = true
                }
            ) {
                Text("Add City")
            }
            // City Deleter Button.
            Button(
                onClick = {
                    //if (newCityName.isNotBlank()) {
                    //    onAddCity(newCityName)
                    //    newCityName = ""
                    //}
                }
            ) {
                Text("Remove City")
            }
        }
        if (showNewCity) {
            Row(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City name") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        if (newCityName.isNotBlank()) {
                            onAddCity(newCityName)
                            newCityName = ""
                        }
                        showNewCity = false
                    }
                ) {
                    Text("Confirm")
                }
            }
        }
        //Spacer(modifier = Modifier.width(8.dp))

        LazyColumn(modifier = modifier
            .fillMaxSize()) {
            //
            //
            items(cities) { city ->
                CityRow(city = city)
            }
        }
    }
}

@Composable
fun CityRow(city: String) {
    Text(
        text = city,
        fontSize = 28.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {

            //Text("Hello World!")
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        modifier = Modifier.padding(innerPadding),
                        onAddCity = { cityRepository.addCity(it) }
                    )
                    //cityRepository.removeCity("Minas Morgul")
                }
            }
        }
    }
}

/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListyCityTheme {
        Greeting("Android")
    }
}
*/
