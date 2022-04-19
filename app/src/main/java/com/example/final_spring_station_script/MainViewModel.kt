package com.example.final_spring_station_script

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.final_spring_station_script.dto.User
import com.google.firebase.firestore.FirebaseFirestore


class MainViewModel (get: Any) : ViewModel(){
    var user: User? = null
    private lateinit var firestore: FirebaseFirestore
    fun saveUser() {user?.let { user ->
        val handle = firestore.collection("users").document(user.uid).set(user)
        handle.addOnSuccessListener { Log.d("Firebase", "User Saved") }
        handle.addOnFailureListener { Log.e("Firebase", "User save failed $it") }

    }
    }
}