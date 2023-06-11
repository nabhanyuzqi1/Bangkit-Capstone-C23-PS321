package com.oneplatform.obeng.utils

import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.oneplatform.obeng.model.Constant

fun getSnapToken(){
    val serverKey = Constant.SERVER_KEY_MIDTRANS
    val clientKey = Constant.CLIENT_KEY_MIDTRANS

// Define transaction details
    val transactionDetails = hashMapOf(
        "order_id" to "ORDER123",
        "gross_amount" to 100000
    )

// Define item details
    val itemDetails = listOf(
        hashMapOf(
            "id" to "ITEM001",
            "price" to 50000,
            "quantity" to 2,
            "name" to "Item 1"
        ),
        hashMapOf(
            "id" to "ITEM002",
            "price" to 25000,
            "quantity" to 1,
            "name" to "Item 2"
        )
    )

// Define customer details
    val customerDetails = hashMapOf(
        "first_name" to "John",
        "last_name" to "Doe",
        "email" to "john.doe@example.com",
        "phone" to "1234567890"
    )

// Create the data object to pass to the Cloud Function
    val data = hashMapOf(
        "serverKey" to serverKey,
        "clientKey" to clientKey,
        "transactionDetails" to transactionDetails,
        "itemDetails" to itemDetails,
        "customerDetails" to customerDetails
    )

// Call the Cloud Function
    generateSnapToken(data)
        .addOnSuccessListener { result ->
            // Retrieve the Snap Token from the result
            val snapToken = result.data as String
            // Use the Snap Token for further processing
            // ...
        }
        .addOnFailureListener { error ->
            // Handle any errors that occurred during the process
            // ...
        }
}