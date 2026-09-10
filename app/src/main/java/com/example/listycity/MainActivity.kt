package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

data class City(val name: String, var isSelected: Boolean = false)
class CityRepository {
    //
    private val _cities = mutableStateListOf(
        City("Minas Tirith"), City("Minas Morgul"), City("The Shire"),
        City("Baradur"), City("Osgiliath"), City("Lothlorien")
    )

    val cities: List<City> get() = _cities

    fun addCity(city: City) {
        //
        _cities.add(city)
    }

    fun removeCities() {
        /* This method removes all selected cities from the array.
         *
         */
        if (_cities.isNotEmpty()) _cities.removeAll { it.isSelected }
    }
}


@Composable
fun CityListScreen(
    cities: List<City>,
    modifier: Modifier = Modifier,
    onAddCity: (City) -> Unit,
    onRemoveCity: () -> Unit
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
                    onRemoveCity()
                }
            ) {
                Text("Remove Selected Cities")
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
                            onAddCity(City(newCityName))
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
            .fillMaxSize()
            .selectableGroup()) {
            //
            //
            items(cities) { city ->
                CityRow(city = city)
                Text(text=city.isSelected.toString())
            }
        }
    }
}

@Composable
fun CityRow(city: City) {
    Text(
        text = city.name,
        fontSize = 28.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
            .background(if (city.isSelected) Color.LightGray else Color.Transparent) // I had to fight this inline if statement.
            .selectable(
                selected = city.isSelected,
                onClick = { city.isSelected = !city.isSelected })
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
                        onAddCity = { cityRepository.addCity(it) },
                        onRemoveCity = {cityRepository.removeCities()}
                    )
                    //cityRepository.removeCity("Minas Morgul")
                }
            }
        }
    }
}