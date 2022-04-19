package com.example.final_spring_station_script

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.final_spring_station_script.dto.User
import com.example.final_spring_station_script.ui.theme.Final_Spring_Station_ScriptTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.firebase.ui.auth.AuthUI

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel<MainViewModel>()
    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel.fetchComputerComponents()
            firebaseUser?.let {
                val user = User(it.uid, "")
                viewModel.user = user
                viewModel.listenToParts()
                Final_Spring_Station_ScriptTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.fillMaxWidth()
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
                label = { Text(stringResource(R.string.partName)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = computerPartType,
                onValueChange = { computerPartType = it },
                label = { Text(stringResource(R.string.partType)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = computerPartBrand,
                onValueChange = { computerPartBrand = it },
                label = { Text(stringResource(R.string.partBrand)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = computerPartRating,
                onValueChange = { computerPartRating = it },
                label = { Text(stringResource(R.string.partRating)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = computerPartPrice,
                onValueChange = { computerPartPrice = it },
                label = { Text(stringResource(R.string.partPrice)) },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "$computerPartName $computerPartType $computerPartBrand $computerPartRating $computerPartPrice",
                        Toast.LENGTH_LONG
                    ).show()
                },
                content = { Text(text = "Save") }
            )


        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        Final_Spring_Station_ScriptTheme {
            CarPartFacts("Android")
        }
    }
}