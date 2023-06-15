package com.oneplatform.obeng.utils

import AuthStateManager
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.oneplatform.obeng.model.FirebaseAuthModel
import com.oneplatform.obeng.model.TechnicianViewModel
import com.oneplatform.obeng.model.User


class FirebaseAuthImpl(private val authStateManager: AuthStateManager) : FirebaseAuthModel {
    override fun loginWithEmail(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailed: (Exception?) -> Unit
    ) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                Log.d("Email:", email)
                Log.d("Password:", password)
                if (task.isSuccessful) {
                    // Save the authentication state as logged in
                    authStateManager.saveAuthState(true)
                    onSuccess.invoke()

                } else {
                    val exception = task.exception
                    onFailed.invoke(exception)
                }
            }
    }


    override fun registerWithEmailCustomer(
        email: String?,
        password: String?,
        username: String?,
        phone: String?,
        address: String?,
        role: String?,
        onSuccess: () -> Unit,
        onFailed: (Exception?) -> Unit
    ) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() || username.isNullOrEmpty() ||
            phone.isNullOrEmpty() || address.isNullOrEmpty() || role.isNullOrEmpty()
        ) {
            // Handle the case where any of the input parameters is null or empty
            onFailed.invoke(null)
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    val authId = user?.uid
                    val firestore = FirebaseFirestore.getInstance()

                    val newUser = User(
                        authId ?: "",
                        email,
                        username,
                        address,
                        role
                    )

                    if (authId != null) {
                        firestore.collection("users").document(authId)
                            .set(newUser)
                            .addOnSuccessListener {
                                onSuccess.invoke()
                            }
                            .addOnFailureListener { exception ->
                                onFailed.invoke(exception)
                            }
                    } else {
                        onFailed.invoke(null)
                    }
                } else {
                    val exception = task.exception
                    onFailed.invoke(exception)
                }
            }
    }



    override fun registerWithEmailTechnician(
        email: String?,
        password: String?,
        username: String?,
        phone: String?,
        address: String?,
        skill: String?,
        certificationLink: String?,
        portfolioLink: String?,
        skillCategory: List<String>?,
        role: String?,
        onSuccess: () -> Unit,
        onFailed: (Exception?) -> Unit
    ) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() || username.isNullOrEmpty() ||
            phone.isNullOrEmpty() || address.isNullOrEmpty() || skill.isNullOrEmpty() ||
            certificationLink.isNullOrEmpty() || portfolioLink.isNullOrEmpty() ||
            skillCategory.isNullOrEmpty() || role.isNullOrEmpty()
        ) {
            // Handle the case where any of the input parameters is null or empty
            onFailed.invoke(null)
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    val authId = user?.uid
                    val firestore = FirebaseFirestore.getInstance()

                    val newTechnician = TechnicianViewModel.Technician(
                        uniqueNumericId = null,
                        role = "technician",
                        noHandphone = phone,
                        linkSertifikasi = certificationLink,
                        fotoProfil = "", // Set the appropriate value for the technician's photo URL
                        keahlian = skill,
                        NIK = "",
                        alamat = address,
                        techPhoto = null, // Set the appropriate value for the technician's photo resource
                        nama = username,
                        name = null, // Set the appropriate value for the technician's name if available
                        jenisKeahlian = arrayListOf(), // Assuming skillCategory is a single string value
                        jenisKendaraan = "", // Set the appropriate value for the technician's vehicle type
                        fotoKTP = "", // Set the appropriate value for the technician's KTP photo URL
                        linkPortofolio = portfolioLink,
                        technicianId = "",
                        email = email,
                        id = null, // Set the appropriate value for the technician's ID if available
                        timestamp = null // Set the appropriate value for the technician's timestamp if available
                    )


                    if (authId != null) {
                        firestore.collection("technicians").document(authId)
                            .set(newTechnician)
                            .addOnSuccessListener {
                                onSuccess.invoke()
                            }
                            .addOnFailureListener { exception ->
                                onFailed.invoke(exception)
                            }
                    } else {
                        onFailed.invoke(null)
                    }
                } else {
                    val exception = task.exception
                    onFailed.invoke(exception)
                }
            }
    }

    override fun logout(onSuccess: () -> Unit, onFailed: (Exception?) -> Unit) {
        FirebaseAuth.getInstance().signOut()
        authStateManager.saveAuthState(false)
        onSuccess.invoke()
    }


}
