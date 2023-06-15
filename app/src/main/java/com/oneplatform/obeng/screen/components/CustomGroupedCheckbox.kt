
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oneplatform.obeng.ui.theme.gray

@Composable
fun CustomStyleGroupedCheckbox(
    mItemsList: List<String>,
    onItemsSelected: (List<String>) -> Unit
) {
    val checkedItems = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxWidth()) {
        mItemsList.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val isChecked = checkedItems.contains(item)

                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            checkedItems.add(item)
                        } else {
                            checkedItems.remove(item)
                        }
                        onItemsSelected(checkedItems.toList())
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.secondary,
                        checkmarkColor = Color.White
                    )
                )

                Text(
                    text = item,
                    color = gray,
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomStyleGroupedCheckboxPreview() {
    CustomStyleGroupedCheckbox(
        mItemsList = listOf(
            "Grouped Checkbox One",
            "Grouped Checkbox Two",
            "Grouped Checkbox Three"
        )
    ) { selectedItems ->
        // Handle the selected items here
        // You can update a state variable or perform any desired action
        println(selectedItems)
    }
}
