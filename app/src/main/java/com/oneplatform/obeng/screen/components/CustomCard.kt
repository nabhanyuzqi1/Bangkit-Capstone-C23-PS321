package com.oneplatform.obeng.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oneplatform.obeng.model.CardOrderStats
import com.oneplatform.obeng.model.CardOrderTypes
import com.oneplatform.obeng.ui.theme.third

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
fun CardHistoryOrderPreview(){
    Column {
        CardHistoryOrder(CardOrderTypes[0])
        CardHistoryOrder(CardOrderTypes[1])
        CardHistoryOrder(CardOrderTypes[2])
        CardHistoryOrder(CardOrderTypes[3])
    }

}
