@file:OptIn(DelicateCoroutinesApi::class)

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.oneplatform.obeng.model.FirebaseAuthModel
import com.oneplatform.obeng.utils.FirebaseAuthImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

fun loginClicked(
    navController: NavController,
    emailState: String,
    passwordState: String,
    context: Context,
    route: String
) {
    if (emailState.isEmpty() || passwordState.isEmpty()) {
        // Email or password is empty
        Toast.makeText(context, "Please enter your email and password", Toast.LENGTH_SHORT).show()
        return
    }

    val authStateManager = AuthStateManager(context = context) // Replace 'context' with the appropriate context
    val firebaseAuthModel: FirebaseAuthModel = FirebaseAuthImpl(authStateManager)
    firebaseAuthModel.loginWithEmail(
        emailState, passwordState,
        onSuccess = {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                val firestore = FirebaseFirestore.getInstance()
                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val userDocumentSnapshot = firestore.collection("users").document(uid).get().await()
                        val technicianDocumentSnapshot = firestore.collection("technicians").document(uid).get().await()

                        if (userDocumentSnapshot.exists()) {
                            val role = userDocumentSnapshot.getString("role")
                            if (role == "customer") {
                                if (route == "home_screen") {
                                    // User is a customer, navigate to the customer page
                                    authStateManager.saveRole(role = role)
                                    Toast.makeText(context, "Logged in as a customer", Toast.LENGTH_SHORT).show()
                                    navController.navigate(route = "home_screen")
                                } else {
                                    Toast.makeText(context, "Please login as a customer", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                // User role is not recognized
                                Toast.makeText(context, "Your role is not recognized", Toast.LENGTH_SHORT).show()
                            }
                        } else if (technicianDocumentSnapshot.exists()) {
                            val role = technicianDocumentSnapshot.getString("role")
                            if (role == "technician") {
                                if (route == "techhome_screen") {
                                    // User is a technician, navigate to the technician page
                                    authStateManager.saveRole(role = role)
                                    Toast.makeText(context, "Logged in as a technician", Toast.LENGTH_SHORT).show()
                                    navController.navigate(route = "techhome_screen")
                                } else {
                                    Toast.makeText(context, "Please login as a technician", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                // Technician role is not recognized
                                Toast.makeText(context, "Your role is not recognized", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Document does not exist for the user or technician, show toast
                            Toast.makeText(context, "Your role is not available", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Error occurred while retrieving the role, show toast
                        Toast.makeText(context, "Failed to retrieve your role", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                        // Handle other error scenarios if needed
                    }
                }
            }
        }
    ) { exception ->
        exception?.printStackTrace()
        if (exception is FirebaseAuthInvalidCredentialsException) {
            // Email or password is incorrect
            Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT).show()
        } else {
            // Handle other error scenarios if needed
            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
        }
    }
}
