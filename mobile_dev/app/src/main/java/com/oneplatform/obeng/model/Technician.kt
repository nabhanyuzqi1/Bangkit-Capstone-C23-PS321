package com.oneplatform.obeng.model

import com.oneplatform.obeng.R

data class Technician(
    val id: String, val name: String, val email: String, val noHandphone: String,
    val keahlian: String, val linkSertifikasi: String, val linkPortofolio: String,
    val jenisKeahlian: String, val techPhoto: Int, val role: String, val alamat: String)


val techDummyData = listOf(
    Technician(
        id = "1",
        name = "Nabhan Yuzqi Al Mubarok",
        email = "nabhanyuzqi1@gmail.com",
        noHandphone = "085950202227",
        keahlian = "Motor",
        jenisKeahlian = "Mesin",
        linkPortofolio = "https://www.linkedin.com/in/nabhanyuzqi/",
        techPhoto = R.drawable.ic_profile_dummy,
        role = "technician",
        linkSertifikasi = "https://dicoding.com",
        alamat = "Jakarta, Indonesia"
    ),
)