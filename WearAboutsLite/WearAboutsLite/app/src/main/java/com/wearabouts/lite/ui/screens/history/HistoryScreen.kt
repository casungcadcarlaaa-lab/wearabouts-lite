package com.wearabouts.lite.ui.screens.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wearabouts.lite.data.local.HistoryAction
import com.wearabouts.lite.data.local.LocationType
import com.wearabouts.lite.data.local.StatusType
import com.wearabouts.lite.data.model.HistoryActivity
import com.wearabouts.lite.ui.components.BottomNavBar
import com.wearabouts.lite.ui.theme.TextSecondary
import com.wearabouts.lite.viewmodel.ClothingViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: ClothingViewModel,
    navController: NavController
) {
    val history by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Activity History",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Serif,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (history.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.History,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No activity yet.", color = TextSecondary)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(history.sortedByDescending { it.timestamp }) { activity ->
                    DetailedHistoryCard(
                        activity = activity,
                        onRestore = { viewModel.restoreFromHistory(activity) }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailedHistoryCard(activity: HistoryActivity, onRestore: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Item Icon/Emoji
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.background,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = activity.itemEmoji, fontSize = 24.sp)
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "You ${activity.action.name.lowercase()} ${activity.itemName}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = getTimeAgo(activity.timestamp),
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                }

                // Action Badge
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = when(activity.action) {
                        HistoryAction.Added -> Color(0xFFF3E8FF)
                        HistoryAction.Updated -> Color(0xFFEFF6FF)
                        HistoryAction.Deleted -> Color(0xFFFEF2F2)
                        HistoryAction.Restored -> Color(0xFFF0FDF4)
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = when(activity.action) {
                                HistoryAction.Added -> Icons.Default.Add
                                HistoryAction.Updated -> Icons.Default.Edit
                                HistoryAction.Deleted -> Icons.Default.Delete
                                HistoryAction.Restored -> Icons.Default.Restore
                            },
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = when(activity.action) {
                                HistoryAction.Added -> Color(0xFF8B5CF6)
                                HistoryAction.Updated -> Color(0xFF3B82F6)
                                HistoryAction.Deleted -> Color(0xFFEF4444)
                                HistoryAction.Restored -> Color(0xFF22C55E)
                            }
                        )
                        Text(
                            text = activity.action.name.lowercase(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = when(activity.action) {
                                HistoryAction.Added -> Color(0xFF8B5CF6)
                                HistoryAction.Updated -> Color(0xFF3B82F6)
                                HistoryAction.Deleted -> Color(0xFFEF4444)
                                HistoryAction.Restored -> Color(0xFF22C55E)
                            }
                        )
                    }
                }

                Icon(
                    Icons.Default.ExpandMore,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotation),
                    tint = TextSecondary
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.LightGray.copy(alpha = 0.3f))
                    
                    // Detailed Info
                    InfoSection(
                        icon = Icons.Default.Event,
                        label = "Full Date & Time",
                        value = SimpleDateFormat("MMMM d, yyyy 'at' h:mm a", Locale.getDefault()).format(Date(activity.timestamp))
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    InfoSection(
                        icon = Icons.Default.Label,
                        label = "Item ID",
                        value = activity.itemId
                    )
                    
                    if (activity.status != null || activity.location != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Default.Place, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFFF87171))
                                Text(text = "Current State", style = MaterialTheme.typography.labelLarge, color = TextSecondary)
                            }
                            Row(
                                modifier = Modifier.padding(top = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (activity.status != null) {
                                    StatusIndicatorSmall(status = activity.status)
                                }
                                if (activity.location != null) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                                        Text(
                                            text = activity.location.name,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Description Box
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                            Text(
                                text = when(activity.action) {
                                    HistoryAction.Added -> "This item was added to your wardrobe."
                                    HistoryAction.Updated -> "The item details were updated."
                                    HistoryAction.Deleted -> "This item was removed from your wardrobe."
                                    HistoryAction.Restored -> "This item was restored to your wardrobe."
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoSection(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFFF87171))
            Text(text = label, style = MaterialTheme.typography.labelLarge, color = TextSecondary)
        }
        Text(
            text = value,
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun StatusIndicatorSmall(status: StatusType) {
    val dotColor = when(status) {
        StatusType.Clean -> Color(0xFF4ADE80)
        StatusType.Used -> Color(0xFFFACC15)
        StatusType.Borrowed -> Color(0xFF60A5FA)
        StatusType.Dirty -> Color(0xFFF87171)
    }
    val bgColor = when(status) {
        StatusType.Clean -> Color(0xFFF0FDF4)
        StatusType.Used -> Color(0xFFFEFCE8)
        StatusType.Borrowed -> Color(0xFFEFF6FF)
        StatusType.Dirty -> Color(0xFFFEF2F2)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(999.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Text(
                text = status.name,
                color = dotColor.copy(alpha = 0.8f),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}

fun getTimeAgo(time: Long): String {
    val diff = System.currentTimeMillis() - time
    return when {
        diff < 60000 -> "Just now"
        diff < 3600000 -> "${diff / 60000}m ago"
        diff < 86400000 -> "${diff / 3600000}h ago"
        else -> SimpleDateFormat("MMM d", Locale.getDefault()).format(Date(time))
    }
}
