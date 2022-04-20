package com.example.final_spring_station_script

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.final_spring_station_script.dto.ComputerComponent
import com.example.final_spring_station_script.dto.User
import com.example.final_spring_station_script.ui.theme.Final_Spring_Station_ScriptTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
//import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.final_spring_station_script.dto.SpecifiedComputerPart

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel<MainViewModel>()
    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var userPickedPart: SpecifiedComputerPart by mutableStateOf(SpecifiedComputerPart())
    private var strSelectedData: String = ""
    private var selectedPart: ComputerComponent? = null
    private var inPartName: String = ""
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
                        ComputerPartFacts("Android")
                    }
                }
            }
        }
    }

    @Composable
    fun ComputerPartFacts(name: String, parts: List<ComputerComponent> = ArrayList<ComputerComponent>(), specifiedComputerPart: List<SpecifiedComputerPart> = ArrayList<SpecifiedComputerPart>(), userPickedPart : SpecifiedComputerPart = SpecifiedComputerPart()){
        var computerPartType by remember(userPickedPart.thisPartId) { mutableStateOf(userPickedPart.thisPartType)}
        var computerPartName by remember(userPickedPart.thisPartId) { mutableStateOf(userPickedPart.thisPartName)}
        var computerPartBrand by remember(userPickedPart.thisPartId) { mutableStateOf(userPickedPart.thisPartBrand)}
        var computerPartPrice by remember(userPickedPart.thisPartId){ mutableStateOf(userPickedPart.thisPartPrice)}
        var computerPartRating by remember(userPickedPart.thisPartId) { mutableStateOf(userPickedPart.thisPartRating)}
        val context = LocalContext.current
        Column() {
            ComputerPartSpinner(ComputerPartsDatabase = specifiedComputerPart)
            TextFieldWithDropdownUsage(dataIn = parts, stringResource(R.string.partName), userPickedPart)
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
                value = computerPartPrice,
                onValueChange = { computerPartPrice = it },
                label = Text("Part Price"),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = computerPartRating,
                onValueChange = { computerPartRating = it },
                label =  Text("Part Rating") ,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    //sign in user
                    signIn()
                    var specifiedComputerPart = SpecifiedComputerPart().apply {
                        thisPartType = computerPartType
                        thisPartName = computerPartName
                        thisPartBrand = computerPartBrand
                        thisPartRating = computerPartRating
                        thisPartPrice = computerPartPrice
                        thisPartId = selectedPart?.let{
                            it.PartIdFinal
                        }?: 0
                    }
                    viewModel.saveParts()
                    Toast.makeText(
                        context,
                        " $computerPartType $computerPartName $computerPartBrand $computerPartPrice $computerPartRating",
                        Toast.LENGTH_LONG
                    ).show()
                },
                content = { Text(text = "Save") }
            )
            viewModel.saveParts()
            Events()
        }
    }
    @Composable
    fun ComputerPartSpinner (ComputerPartsDatabase: List<SpecifiedComputerPart>){
        var computerPartText by remember {mutableStateOf("My Car Inventory")}
        var expanded by remember { mutableStateOf(false)}
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(Modifier
                .padding(26.dp)
                .clickable {
                    expanded = !expanded
                }
                .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = computerPartText, fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
                DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}){
                    ComputerPartsDatabase.forEach {
                            specifiedComputerPart-> DropdownMenuItem(onClick = {
                        expanded = false
                        computerPartText = specifiedComputerPart.toString()
                        userPickedPart = specifiedComputerPart
                    }) {
                        Text(text = specifiedComputerPart.toString())
                    }
                    }
                }
            }
        }
    }
    @Composable
            fun TextFieldWithDropdownUsage(dataIn: List<ComputerComponent>, label: String = "", userPickedPart: SpecifiedComputerPart) {
            val dropDownOptions = remember { mutableStateOf(listOf<ComputerComponent>()) }
            val textFieldValue = remember(userPickedPart.thisPartId) { mutableStateOf(TextFieldValue(userPickedPart.thisPartName)) }
            val dropDownExpanded = remember { mutableStateOf(false) }

            fun onDropdownDismissRequest() {
                dropDownExpanded.value = false
            }

            fun onValueChanged(value: TextFieldValue) {
                strSelectedData = value.text
                inPartName = value.text
                dropDownExpanded.value = true
                textFieldValue.value = value
                dropDownOptions.value = dataIn.filter {
                    it.toString().startsWith(value.text) && it.toString() != value.text
                }.take(3)
            }
            TextFieldWithDropdown(
                modifier = Modifier.fillMaxWidth(),
                value = textFieldValue.value,
                setValue = ::onValueChanged,
                onDismissRequest = ::onDropdownDismissRequest,
                dropDownExpanded = dropDownExpanded.value,
                list = dropDownOptions.value,
                label = label
            )
        }
        @Composable
        fun TextFieldWithDropdown(
            modifier: Modifier = Modifier,
            value: TextFieldValue,
            setValue: (TextFieldValue)-> Unit,
            onDismissRequest: ()-> Unit,
            dropDownExpanded: Boolean,
            list: List<ComputerComponent>,
            label: String = ""
        ) {
            Box(modifier) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            if (!focusState.isFocused)
                                onDismissRequest()
                        },
                    value = value,
                    onValueChange = setValue,
                    label = { Text(label) },
                    colors = TextFieldDefaults.outlinedTextFieldColors()
                )
                DropdownMenu(
                    expanded = dropDownExpanded,
                    properties = PopupProperties(
                        focusable = false,
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    ),
                    onDismissRequest = onDismissRequest
                ) {
                    list.forEach { text->
                            DropdownMenuItem(onClick = {
                                setValue(
                                    TextFieldValue(
                                        text.toString(),
                                        TextRange(text.toString().length)
                                    )
                                )
                                selectedPart = text
                            }) {
                                //Text(text = text.toString())
                                //}
                                Text(text = text.toString())

                            }
                    }
                }
            }
        }

    @Composable
    private fun Events() {
        val parts by viewModel.components.observeAsState(initial = emptyList())
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), modifier = Modifier.fillMaxHeight()) {
            items (
                items = parts,
                itemContent = {
                    EventListItem(computerComponent = it)
                }
            )
        }
    }
    @Composable
    fun EventListItem(computerComponent : ComputerComponent)
    {
        var inDescription by remember(computerComponent.Name) {mutableStateOf(computerComponent.Brand)}
        Row{
            Column(Modifier.weight(2f)) {
                Text(text= "")
            }
            Column(Modifier.weight(4f)) {
                Text(text= computerComponent.Name, style= MaterialTheme.typography.h6)
                Text(text= computerComponent.Brand, style= MaterialTheme.typography.h6)
               /* OutlinedTextField(value = inDescription,
                    onValueChange = {inDescription = it},
                    label  = { Text(stringResource(R.string.Name))},
                    modifier = Modifier.fillMaxWidth()
                )*/
            }
            Column(Modifier.weight(1f)) {
                Button (
                    onClick = {
                        computerComponent.Brand = inDescription
                        viewModel.updatePartsDatabase(computerComponent)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Save",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }
    private fun signIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val signinIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signinIntent)
    }
    private val signInLauncher = registerForActivityResult (
        FirebaseAuthUIActivityResultContract()
    ) {

            res -> this.signInResult(res)
    }
    private fun signInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            firebaseUser = FirebaseAuth.getInstance().currentUser
            firebaseUser?.let {
                val user = User(it.uid, it.displayName)
                viewModel.user = user
                viewModel.saveUser()
                viewModel.listenToParts()
            }
        } else {
            Log.e("MainActivity.kt", "Error logging in " + response?.error?.errorCode)

        }
    }

        @Preview(showBackground = true)
        @Composable
        fun DefaultPreview() {
            Final_Spring_Station_ScriptTheme {
                ComputerPartFacts("Android")
            }
        }
}
