package com.example.jetareader.repository

import android.util.Log
import com.example.jetareader.data.DataOrException
import com.example.jetareader.model.MBook
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FireRepository @Inject constructor(
    private val query: Query
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try{
            dataOrException.loading = true
            dataOrException.data = query.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
             if(!dataOrException.data.isNullOrEmpty())dataOrException.loading = false
        }catch (exception : FirebaseFirestoreException)
        {
            dataOrException.e = exception
            dataOrException.loading = false
        }
        return dataOrException
    }
}

//return suspendCancellableCoroutine { continuation ->
//    try {
//        dataOrException.loading = true
//        val myRef = FirebaseDatabase.getInstance().getReference("books")
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    dataOrException.data = snapshot.children.map { dataSnapshot ->
//                        dataSnapshot.getValue(MBook::class.java)!!
//                    }
//                    dataOrException.loading = false
//                    Log.d("GET_DATA", "onDataChange: ${dataOrException.toString()}")
//                    continuation.resume(dataOrException, null)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                dataOrException.e = Exception(error.message)
//                continuation.resume(dataOrException, null)
//            }
//        })
//    } catch (exception: Exception) {
//        dataOrException.e = exception
//        continuation.resume(dataOrException, null)
//    }
//}