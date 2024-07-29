package com.example.jetareader.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.jetareader.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    // For Sign In
    fun signInWithEmailAndPassword(email: String, password: String,home:() -> Unit)
    = viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Log.d("FB", "signInWithEmailAndPassword: yessssssss! ${task.result.toString()}")
                        home()
                    }else{
                        Log.d("FB", "signInWithEmailAndPassword: ${task.result.toString()}")
                    }

                }
        } catch (ex: Exception) {
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
        }
    }

    // store user name along with user UID in firebase database


    //For Sign Up or create account
    fun createUserWithEmailAndPassword(email:String,password: String,home: () -> Unit){
        if(_loading.value == false)
        {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful)
                    {
                        Log.d("FB", "createUserWithEmailAndPassword: ${task.result}")
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(displayName)
                        home()
                    }else{
                        Log.d("FB", "createUserWithEmailAndPassword: ${task.result}")
                    }
                    _loading.value = false
                }
        }
    }
    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = MUser(userId = userId.toString(), displayName = displayName.toString(), avatarUrl = "", quote = "I am a hero",
            profession = "Android Developer", id = null).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("FB","onCreate: ${it.id}")
            }
            .addOnFailureListener {
                Log.d("FB","onCreate: $it")
            }
    }

}


/*
//    private val _loadingState = MutableLiveData<LoadingState>()
//    val loadingState: LiveData<LoadingState> get() = _loadingState
 */