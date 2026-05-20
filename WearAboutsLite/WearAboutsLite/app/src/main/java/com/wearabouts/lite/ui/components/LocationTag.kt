package com.wearabouts.lite.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wearabouts.lite.data.local.LocationType
import com.wearabouts.lite.ui.theme.TextSecondary

@Composable
fun LocationTag(location: LocationType) {
    val locationText = when (location) {
        LocationType.Cabinet -> "Cabinet"
        LocationType.LaundryBasket -> "Laundry Basket"
        LocationType.CurrentlyWearing -> "Currently Wearing"
        LocationType.Borrowed -> "Borrowed"
    }
    
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = locationText,
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
