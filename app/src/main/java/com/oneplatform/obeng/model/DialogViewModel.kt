package com.oneplatform.obeng.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {

    var isDialogShown by mutableStateOf(false)
        private set

    fun onClicked(){
        isDialogShown = true
    }

    fun onDismissDialog(){
        isDialogShown = false
    }
}