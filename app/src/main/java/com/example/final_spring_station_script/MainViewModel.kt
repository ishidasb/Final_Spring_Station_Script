package com.example.final_spring_station_script

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_spring_station_script.dto.ComputerComponent
import com.example.final_spring_station_script.dto.User
import com.example.final_spring_station_script.service.ComponentService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.launch


class MainViewModel (get: Any) : ViewModel(){


    var componentService: ComponentService = ComponentService()
    val NEW_Computer = "New Component"
    var components: MutableLiveData<List<ComputerComponent>> =
        MutableLiveData<List<ComputerComponent>>()
    var user: User? = null
    var computerComponent by mutableStateOf(ComputerComponent())
    private lateinit var firestore: FirebaseFirestore
    //val eventParts : MutableLiveData<List<ComputerComponent>> = MutableLiveData<List<ComputerComponent>>()

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    fun fetchComputerComponents() {
        viewModelScope.launch {
            var innerComponents = componentService.fetchComputerComponents()
            components.postValue(innerComponents)
        }
    }
    fun saveParts() {
        user?.let { user ->
            val document =
                if (computerComponent.Type == null || computerComponent.Type.isEmpty()) {
                    // insert
                    firestore.collection("ComputerPartsDatabase").document(user.uid).collection("Type")
                        .document()
                } else {
                    // update
                    firestore.collection("ComputerPartsDatabase").document(user.uid).collection("Type")
                        .document(computerComponent.Type)
                }
        }
    }
    fun saveUser() {user?.let { user ->
        val handle = firestore.collection("ComputerPartsDatabase").document(user.uid).set(user)
        handle.addOnSuccessListener { Log.d("Firebase", "User Saved") }
        handle.addOnFailureListener { Log.e("Firebase", "User save failed $it") }
        }
    }

    fun listenToParts() {
        user?.let { user ->
            firestore.collection("users").document(user.uid).collection("Type")
                .addSnapshotListener { snapshot, error ->
                    // see of we received an error
                    if (error != null) {
                        Log.w("listen failed.", error)
                        return@addSnapshotListener
                    }
                    // if we reached this point, there was not an error, and we have data.
                    snapshot?.let {
                        val allParts = ArrayList<ComputerComponent>()
                        allParts.add(ComputerComponent(NEW_Computer))
                        val documents = snapshot.documents
                        documents.forEach {
                            val computer = it.toObject(ComputerComponent::class.java)
                            computer?.let {
                                allParts.add(computer)
                            }
                        }
                        components.value = allParts
                    }
                }
        }
    }
     internal fun updatePartsDatabase(computerComponent : ComputerComponent) {
        user?.let { user ->
            val photoDocument =
                if (computerComponent.Name.isEmpty()) {
                    // we need to create a new document.
                    firestore.collection("users").document(user.uid).collection("Type")
                        .document(computerComponent.Type).collection("name").document()
                } else {
                    // update existing document
                    firestore.collection("users").document(user.uid).collection("Type")
                        .document(computerComponent.Type).collection("name").document(computerComponent.Name)
                }
            computerComponent.Name = photoDocument.id
            val handle = photoDocument.set(computerComponent)
            handle.addOnSuccessListener {
                Log.i(ContentValues.TAG, "Successfully updated computer parts metadata")
            }
            handle.addOnFailureListener {
                Log.e(ContentValues.TAG, "Failed to update updated computer parts metadata  ${it.message}")
            }
        }
    }
}