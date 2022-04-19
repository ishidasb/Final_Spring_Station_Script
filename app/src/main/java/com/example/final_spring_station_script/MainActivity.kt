package com.example.final_spring_station_script

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.final_spring_station_script.ui.theme.Final_Spring_Station_ScriptTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Final_Spring_Station_ScriptTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CarPartFacts("Android")
                }
            }
        }
    }
}

@Composable
fun CarPartFacts(name: String) {
    var computerPartName by remember { mutableStateOf(" ") }
    var computerPartType by remember { mutableStateOf("") }
    var computerPartBrand by remember { mutableStateOf("") }
    var computerPartRating by remember { mutableStateOf("") }
    var computerPartPrice by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column() {

        OutlinedTextField(
            value = computerPartName,
            onValueChange = { computerPartName = it },
            label =  {Text(stringResource(R.string.partName))}
        )
        /*
        OutlinedTextField(
            value = computerPartType,
            onValueChange = { computerPartType = it },
            label = { Text(stringResource(R.string.partModel)) }
        )
        OutlinedTextField(
            value = computerPartBrand,
            onValueChange = { computerPartBrand = it },
            label = { Text(stringResource(R.string.partBrand)) }
        )
        OutlinedTextField(
            value = computerPartRating,
            onValueChange = { computerPartRating = it },
            label = { Text(stringResource(R.string.carMake)) }
        )
        OutlinedTextField(
            value = computerPartPrice,
            onValueChange = { computerPartPrice = it },
            label = { Text(stringResource(R.string.partPrice)) }
        )
        Button (
            onClick = {
                Toast.makeText(context, "$computerPartName $computerPartType $computerPartBrand $computerPartRating $computerPartPrice", Toast.LENGTH_LONG).show()
            },
            content = { Text(text = "Save")}
        )

         */

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Final_Spring_Station_ScriptTheme {
        CarPartFacts("Android")
    }
}