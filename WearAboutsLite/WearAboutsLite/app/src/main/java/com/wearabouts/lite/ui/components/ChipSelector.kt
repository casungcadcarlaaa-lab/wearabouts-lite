package com.wearabouts.lite.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wearabouts.lite.ui.theme.Primary
import com.wearabouts.lite.ui.theme.Surface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ChipSelector(
    options: List<T>,
    selected: T,
    onSelect: (T) -> Unit,
    label: (T) -> String,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 0.dp)
    ) {
        items(options) { option ->
            val isSelected = option == selected
            FilterChip(
                selected = isSelected,
                onClick = { onSelect(option) },
                label = { Text(label(option)) },
                modifier = Modifier.bounceClick(),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Primary,
                    selectedLabelColor = Surface,
                    containerColor = Color.Transparent,
                    labelColor = Primary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Primary,
                    selectedBorderColor = Primary
                )
            )
        }
    }
}
