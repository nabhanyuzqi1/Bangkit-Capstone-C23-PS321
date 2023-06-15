package com.oneplatform.obeng.model


import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneplatform.obeng.api.TechnicianApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TechnicianViewModel(private val apiService: TechnicianApiService) : ViewModel() {
    var selectedTechnician: Technician? = null
        set(value) {
            field = value
            if (value != null) {
                fetchTechnicianById(value.technicianId)
            }
        }
    private val _technicians = mutableStateListOf<Technician>()
    val technicians: List<Technician> get() = _technicians

    private var isCacheInitialized = false

    init {
        fetchTechnicians()
    }

    fun fetchTechnicians() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isCacheInitialized) {
                    val fetchedTechnicians = apiService.getTechnicians()
                    _technicians.clear()
                    _technicians.addAll(fetchedTechnicians)
                    isCacheInitialized = true
                }
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

    fun fetchTechnicianById(technicianId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedTechnician = apiService.getTechnicianById(technicianId)
                selectedTechnician = fetchedTechnician
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

    val techDummyData = listOf(
    Technician(
        uniqueNumericId = 1,
        role = "technician",
        noHandphone = "085950202227",
        linkSertifikasi = "https://dicoding.com",
        fotoProfil = "",
        keahlian = "Motor",
        NIK = "",
        alamat = "Jakarta",
        techPhoto = null,
        nama = "Nabhan Yuzqi Al Mubarok",
        name = "",
        jenisKeahlian = arrayListOf(),
        jenisKendaraan = "",
        fotoKTP = "",
        linkPortofolio = "https://www.linkedin.com/in/nabhanyuzqi/",
        technicianId = "",
        email = "nabhanyuzqi1@gmail.com",
        id = "1",
        timestamp = null
    )
)

data class Technician(
    val uniqueNumericId: Int?,
    val role: String,
    val noHandphone: String,
    val linkSertifikasi: String,
    val fotoProfil: String,
    val keahlian: String?,
    val NIK: String,
    val alamat: String?,
    var techPhoto: Bitmap?,
    val nama: String?,
    val name: String?,
    val jenisKeahlian: ArrayList<String>,
    val jenisKendaraan: String,
    val fotoKTP: String,
    val linkPortofolio: String,
    val technicianId: String,
    val email: String,
    val id: String?,
    val timestamp: Timestamp?
)

data class Timestamp(
    val _seconds: Long,
    val _nanoseconds: Long
)}

