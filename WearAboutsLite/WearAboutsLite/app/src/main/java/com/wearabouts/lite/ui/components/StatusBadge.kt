package com.wearabouts.lite.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wearabouts.lite.data.local.StatusType

@Composable
fun StatusBadge(status: StatusType) {
    val (colors, label) = when (status) {
        StatusType.Clean -> listOf(Color(0xFF81E9B4), Color(0xFF22C55E)) to "Clean"
        StatusType.Used -> listOf(Color(0xFFFDE68A), Color(0xFFEAB308)) to "Used"
        StatusType.Borrowed -> listOf(Color(0xFF93C5FD), Color(0xFF3B82F6)) to "Borrowed"
        StatusType.Dirty -> listOf(Color(0xFFA1887F), Color(0xFF795548)) to "Dirty"
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = colors,
                        radius = 15f
                    )
                )
        )
        Text(
            text = label,
            color = colors.last(),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}
