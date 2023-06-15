@file:OptIn(ExperimentalCoilApi::class)

package com.oneplatform.obeng.screen.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.oneplatform.obeng.R
import com.oneplatform.obeng.model.CardOrderStats
import com.oneplatform.obeng.model.CardOrderTypes
import com.oneplatform.obeng.model.TechnicianViewModel
import com.oneplatform.obeng.ui.theme.primary
import com.oneplatform.obeng.ui.theme.third


@Composable
fun CustomUserProfileCard(context: Context = LocalContext.current.applicationContext) {
    // This indicates if the optionsList has data or not
    // Initially, the list is empty. So, its value is false.
    val listPrepared by remember {
        mutableStateOf(true)
    }
    if (listPrepared) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                // User's image, name, email and edit button
                UserDetails(context = context)
            }
        }
    }
}

@Composable
fun CustomTechProfileCard(context: Context = LocalContext.current.applicationContext){
    // This indicates if the optionsList has data or not
    // Initially, the list is empty. So, its value is false.
    val listPrepared by remember {
        mutableStateOf(true)
    }
    if (listPrepared) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
                // User's image, name, email and edit button
                TechDetails(context = context)
        }
    }
}
@Composable
private fun TechDetails(context: Context){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 3f, fill = false)
                    .padding(start = 16.dp)
            ) {

                // User's name
                Text(
                    text = "Nabhan",
                    style = TextStyle(
                        fontSize = 22.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                // User's email
                Text(
                    text = "email123@email.com",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                        letterSpacing = (0.8).sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // User's phone
                Text(
                    text = "085750126896",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                        letterSpacing = (0.8).sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User's image
            Image(
                modifier = Modifier
                    .size(72.dp)
                    .clip(shape = CircleShape)
                    .background(color = third)
                    .padding(5.dp),
                painter = painterResource(id = R.drawable.ic_profile_dummy),
                contentDescription = "Your Image",

                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTechProfileCardPreview(){
    CustomTechProfileCard()
}

// This composable displays user's image, name, email and edit button
@Composable
private fun UserDetails(context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // User's image
        Image(
            modifier = Modifier
                .size(72.dp)
                .clip(shape = CircleShape)
                .background(color = third)
                .padding(5.dp),
            painter = painterResource(id = R.drawable.ic_profile_dummy),
            contentDescription = "Your Image",

            )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 3f, fill = false)
                    .padding(start = 16.dp)
            ) {

                // User's name
                Text(
                    text = "Nabhan",
                    style = TextStyle(
                        fontSize = 22.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                // User's email
                Text(
                    text = "email123@email.com",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                        letterSpacing = (0.8).sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Edit button
            IconButton(
                modifier = Modifier
                    .weight(weight = 1f, fill = false),
                onClick = {
                    Toast.makeText(context, "Edit Button", Toast.LENGTH_SHORT).show()
                }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit Details",
                    tint = primary
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomProfileCardPreview(){
    CustomUserProfileCard()
}

@Composable
fun CardHistoryOrder(cardOrderStats: CardOrderStats) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
        colors = CardDefaults.cardColors(third)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.W900,
                                    color = Color(0xFF4552B8)
                                )
                            ) {
                                append(cardOrderStats.name)
                            }
                        }
                    )
                    Icon(
                        imageVector = cardOrderStats.icon,
                        contentDescription = "Status ${cardOrderStats.status}"
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "No Pesanan")
                    Text(text = "d32dad")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Waktu Pesanan")
                    Text(text = "23:45")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Tanggal Pesanan")
                    Text(text = "5 Juli 2023")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPaymentPreview(){
}
@Composable
fun CardPayment(cardTechStats: TechnicianViewModel.Technician){
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { },
            colors = CardDefaults.cardColors(third)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.W900,
                                        color = Color(0xFF4552B8)
                                    )
                                ) {
                                    append("Biaya")
                                }
                            }
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Jasa")
                        Text(text = "Rp. 150.000")
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Layanan")
                        Text(text = "Rp. 15.000")
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Total", fontWeight = FontWeight.Bold)
                        Text(text = "Rp. 165.000",fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CardTechName(cardTechStats: TechnicianViewModel.Technician?){
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { },
            colors = CardDefaults.cardColors(third)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {

                Column(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.W900,
                                        color = Color(0xFF4552B8)
                                    )
                                ) {
                                    append("Detail Teknisi")
                                }
                            }
                        )

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(text = "Rating")
                        }
                        Row (verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null
                            )
                            Text(text = "5.0")
                        }

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Keahlian")
                        Text(text = "cardTechStats.jenisKeahlian.joinToString()")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        /*Text(text = "Alamat")
                        cardTechStats.alamat?.let { Text(text = it) }*/
                    }

                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { },
            colors = CardDefaults.cardColors(third)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {

                Column(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.W900,
                                        color = Color(0xFF4552B8)
                                    )
                                ) {
                                    append("Data Keahlian")
                                }
                            }
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Sertifikasi")
                        //SimpleClickableText(linkUrl = cardTechStats.linkSertifikasi, textName = "Click Me")
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Portofolio")
                        //(linkUrl = cardTechStats.linkPortofolio, textName = "Click Me")
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.W900,
                                        color = Color(0xFF4552B8)
                                    )
                                ) {
                                    append("Biaya Jasa")
                                }
                            }
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Sewa Jasa")
                        Text(text = "Rp. 150.000")
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleClickableText(linkUrl: String, textName: String) {
    val context = LocalContext.current

    ClickableText(
        text = AnnotatedString(textName),
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
            context.startActivity(intent)
        }
    )
}


@Composable
fun ImagePicker() {
    CardViewImagePicker()
}

@Composable
fun CardViewImagePicker() {
    var selectImages by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            selectImages = uris
        }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { galleryLauncher.launch("image/*") },
            colors = CardDefaults.cardColors(third)
        ) {
            Box(contentAlignment = Alignment.BottomCenter) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(50.dp),
                    painter = painterResource(id = R.drawable.ic_camera_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                    )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        colors = ButtonDefaults.buttonColors(primary)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_picture),
                            contentDescription = null
                        )
                    }
                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        colors = ButtonDefaults.buttonColors(primary)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = null
                        )
                    }
                }
            }

            LazyRow() {
                items(selectImages) { uri ->
                    Image(
                        painter = rememberImagePainter(uri),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .size(100.dp)
                            .clickable {

                            }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ImagePickerPreview() {
    ImagePicker()
}

@Preview(showBackground = true)
@Composable
fun CardTechNamePreview(){
    //CardTechName(techDummyData[0])
}

@Preview(showBackground = true)
@Composable
fun CardHistoryOrderPreview(){
    Column {
        CardHistoryOrder(CardOrderTypes[0])
        CardHistoryOrder(CardOrderTypes[1])
        CardHistoryOrder(CardOrderTypes[2])
        CardHistoryOrder(CardOrderTypes[3])
    }

}
