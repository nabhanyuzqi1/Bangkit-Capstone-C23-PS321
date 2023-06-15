package com.oneplatform.obeng.model


interface FirebaseAuthModel {
    fun loginWithEmail(email: String, password: String, onSuccess: () -> Unit, onFailed: (Exception?) -> Unit)

    fun registerWithEmailCustomer(
        email: String?,
        password: String?,
        username: String?,
        phone: String?,
        address: String?,
        role: String?,
        onSuccess: () -> Unit,
        onFailed: (Exception?) -> Unit
    )

    fun logout(onSuccess: () -> Unit, onFailed: (Exception?) -> Unit)
    fun registerWithEmailTechnician(
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
    )
}


