package com.wearabouts.lite.ui.screens.edititem

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.wearabouts.lite.data.local.LocationType
import com.wearabouts.lite.data.local.StatusType
import com.wearabouts.lite.data.model.ClothingItem
import com.wearabouts.lite.ui.components.PrimaryButton
import com.wearabouts.lite.ui.theme.*
import com.wearabouts.lite.viewmodel.ClothingViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditItemScreen(
    itemId: String,
    viewModel: ClothingViewModel,
    onNavigateBack: () -> Unit
) {
    val items by viewModel.allItems.collectAsState()
    val originalItem = items.find { it.id == itemId } ?: return

    var status by remember { mutableStateOf(originalItem.status) }
    var location by remember { mutableStateOf(originalItem.location) }
    var showStatusSheet by remember { mutableStateOf(false) }
    var showLocationSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Item") },
            text = { Text("Are you sure you want to delete this item? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteItem(originalItem)
                    showDeleteDialog = false
                    onNavigateBack()
                }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showStatusSheet) {
        StatusUpdateSheet(
            currentStatus = status,
            onDismiss = { showStatusSheet = false },
            onSave = { 
                status = it
                showStatusSheet = false
            }
        )
    }

    if (showLocationSheet) {
        LocationUpdateSheet(
            currentLocation = location,
            onDismiss = { showLocationSheet = false },
            onSave = {
                location = it
                showLocationSheet = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Item Image/Emoji
            Surface(
                modifier = Modifier.size(120.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (originalItem.photoUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(originalItem.photoUri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(text = originalItem.emoji, fontSize = 64.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Item Name
            Text(
                text = originalItem.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A365D)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Category Badge
            Surface(
                shape = RoundedCornerShape(999.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
            ) {
                Text(
                    text = originalItem.category,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Status Card
            EditSectionCard(
                label = "Current Status",
                buttonText = "Change Status",
                onClick = { showStatusSheet = true }
            ) {
                StatusIndicator(status = status)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Location Card
            EditSectionCard(
                label = "Current Location",
                buttonText = "Update Location",
                onClick = { showLocationSheet = true }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when(location) {
                            LocationType.Cabinet -> "Cabinet"
                            LocationType.LaundryBasket -> "Laundry Basket"
                            LocationType.CurrentlyWearing -> "Currently Wearing"
                            LocationType.Borrowed -> "Borrowed"
                        },
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A365D)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            val sdf = SimpleDateFormat("M/d/yyyy", Locale.getDefault())
            Text(
                text = "Last updated: ${sdf.format(Date(originalItem.lastUpdated))}",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Save Item",
                onClick = {
                    val updatedItem = originalItem.copy(
                        status = status,
                        location = location,
                        lastUpdated = System.currentTimeMillis()
                    )
                    viewModel.updateItem(updatedItem, originalItem)
                    onNavigateBack()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { showDeleteDialog = true }) {
                Text("Delete Item", color = Color(0xFFFF4D4D), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun EditSectionCard(
    label: String,
    buttonText: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Primary)
            ) {
                Text(buttonText, color = Primary, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun StatusIndicator(status: StatusType) {
    val (dotColor, bgColor, label) = when(status) {
        StatusType.Clean -> Triple(Color(0xFF4ADE80), Color(0xFFF0FDF4), "Clean")
        StatusType.Used -> Triple(Color(0xFFFACC15), Color(0xFFFEFCE8), "Used")
        StatusType.Borrowed -> Triple(Color(0xFF60A5FA), Color(0xFFEFF6FF), "Borrowed")
        StatusType.Dirty -> Triple(Color(0xFF964B00), Color(0xFFEFEBE9), "Dirty")
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(999.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = dotColor.copy(alpha = 0.8f),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusUpdateSheet(
    currentStatus: StatusType,
    onDismiss: () -> Unit,
    onSave: (StatusType) -> Unit
) {
    var selectedStatus by remember { mutableStateOf(currentStatus) }
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(modifier = Modifier.padding(24.dp).padding(bottom = 32.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Update Status",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A365D)
                    )
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            StatusType.values().filter { it != StatusType.Dirty }.forEach { status ->
                StatusRow(
                    status = status,
                    isSelected = selectedStatus == status,
                    onClick = { selectedStatus = status }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            PrimaryButton(text = "Save", onClick = { onSave(selectedStatus) })
            
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Cancel", color = TextSecondary)
            }
        }
    }
}

@Composable
fun StatusRow(status: StatusType, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) Color(0xFFF0FDF4) else Color.Transparent,
        border = if (isSelected) null else BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatusIndicator(status = status)
            if (isSelected) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF4ADE80))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationUpdateSheet(
    currentLocation: LocationType,
    onDismiss: () -> Unit,
    onSave: (LocationType) -> Unit
) {
    var selectedLoc by remember { mutableStateOf(currentLocation) }
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(modifier = Modifier.padding(24.dp).padding(bottom = 32.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Update Location",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A365D)
                    )
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            LocationType.values().forEach { loc ->
                LocationRow(
                    loc = loc,
                    isSelected = selectedLoc == loc,
                    onClick = { selectedLoc = loc }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            PrimaryButton(text = "Save", onClick = { onSave(selectedLoc) })
            
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Cancel", color = TextSecondary)
            }
        }
    }
}

@Composable
fun LocationRow(loc: LocationType, isSelected: Boolean, onClick: () -> Unit) {
    val (emoji, label) = when(loc) {
        LocationType.Cabinet -> "🗄️" to "Cabinet"
        LocationType.LaundryBasket -> "🧺" to "Laundry Basket"
        LocationType.CurrentlyWearing -> "👕" to "Currently Wearing"
        LocationType.Borrowed -> "🤝" to "Borrowed"
    }
    
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) Color(0xFFF0FDF4) else Color.Transparent,
        border = if (isSelected) null else BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(emoji, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Text(label, fontWeight = FontWeight.Medium, color = Color(0xFF1A365D))
            }
            if (isSelected) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF4ADE80))
            }
        }
    }
}
