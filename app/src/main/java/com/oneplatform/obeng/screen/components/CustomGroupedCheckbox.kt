package com.oneplatform.obeng.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.oneplatform.obeng.ui.theme.gray

@Composable
fun CustomStyleGroupedCheckbox(mItemsList: List<String>) {

    mItemsList.forEach { items ->
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            val isChecked = remember { mutableStateOf(false) }

            Checkbox(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = it },
                enabled = true,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.secondary,
                    checkmarkColor = Color.White

                )
            )
            Text(color = gray,text = items)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CustomStyleGroupedCheckboxPreview(){
    CustomStyleGroupedCheckbox(mItemsList = listOf(
        "Grouped Checkbox One",
        "Grouped Checkbox Two",
        "Grouped Checkbox Three"
    ))
}