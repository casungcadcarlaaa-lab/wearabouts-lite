package com.wearabouts.lite.ui.screens.itemdetail

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.wearabouts.lite.data.local.LocationType
import com.wearabouts.lite.data.local.StatusType
import com.wearabouts.lite.ui.components.PrimaryButton
import com.wearabouts.lite.ui.theme.*
import com.wearabouts.lite.viewmodel.ClothingViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: String,
    viewModel: ClothingViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (String) -> Unit
) {
    val items by viewModel.allItems.collectAsState()
    val item = items.find { it.id == itemId } ?: return

    var status by remember { mutableStateOf(item.status) }
    var location by remember { mutableStateOf(item.location) }
    var borrowedBy by remember { mutableStateOf(item.borrowedBy ?: "") }
    
    var showStatusSheet by remember { mutableStateOf(false) }
    var showLocationSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Sync state with item
    LaunchedEffect(item) {
        status = item.status
        location = item.location
        borrowedBy = item.borrowedBy ?: ""
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Item") },
            text = { Text("Are you sure you want to delete this item? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteItem(item)
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
                if (it == StatusType.Borrowed) {
                    location = LocationType.Borrowed
                }
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
                if (it == LocationType.Borrowed) {
                    status = StatusType.Borrowed
                }
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Item Image/Emoji Card
            Surface(
                modifier = Modifier.size(120.dp),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (item.photoUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(item.photoUri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(text = item.emoji, fontSize = 64.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Item Name
            Text(
                text = item.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Category Badge
            Surface(
                shape = RoundedCornerShape(999.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            ) {
                Text(
                    text = item.category,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Status Card
            DetailSectionCard(
                label = "Current Status",
                buttonText = "Change Status",
                onClick = { showStatusSheet = true }
            ) {
                StatusIndicator(status = status)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Location Card
            DetailSectionCard(
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
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Last updated: ${formatLastUpdated(item.lastUpdated)}",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Borrowed By Card - Show if status is Borrowed or if it has a value
            if (status == StatusType.Borrowed || borrowedBy.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Borrowed By",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextSecondary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color(0xFF483D8B),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            // Editable Borrowed By
                            TextField(
                                value = borrowedBy,
                                onValueChange = { borrowedBy = it },
                                placeholder = { Text("Who borrowed this?", color = TextSecondary) },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                                ),
                                textStyle = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 18.sp
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                    }
                }
            }

            PrimaryButton(
                text = "Save Item",
                onClick = {
                    val updatedItem = item.copy(
                        status = status,
                        location = location,
                        borrowedBy = borrowedBy.takeIf { it.isNotBlank() },
                        lastUpdated = System.currentTimeMillis()
                    )
                    viewModel.updateItem(updatedItem, item)
                    onNavigateBack()
                },
                modifier = Modifier.height(56.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Delete Item",
                    color = Color(0xFFFF4D4D),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

private fun formatLastUpdated(timestamp: Long): String {
    val now = Calendar.getInstance()
    val lastUpdated = Calendar.getInstance().apply { timeInMillis = timestamp }
    
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val timeString = timeFormat.format(Date(timestamp))
    
    return if (now.get(Calendar.YEAR) == lastUpdated.get(Calendar.YEAR) &&
        now.get(Calendar.DAY_OF_YEAR) == lastUpdated.get(Calendar.DAY_OF_YEAR)
    ) {
        "Today, $timeString"
    } else {
        val dateFormat = SimpleDateFormat("M/d/yyyy", Locale.getDefault())
        dateFormat.format(Date(timestamp))
    }
}

@Composable
fun DetailSectionCard(
    label: String,
    buttonText: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
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
        StatusType.Dirty -> Triple(Color(0xFFF87171), Color(0xFFFEF2F2), "Dirty")
    }

    Surface(
        color = if (MaterialTheme.colorScheme.brightness == Brightness.Dark) MaterialTheme.colorScheme.surfaceVariant else bgColor,
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
                color = if (MaterialTheme.colorScheme.brightness == Brightness.Dark) Color.White else dotColor.copy(alpha = 0.8f),
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
        containerColor = MaterialTheme.colorScheme.surface,
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
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            StatusType.values().forEach { status ->
                StatusOptionItem(
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
fun StatusOptionItem(status: StatusType, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent,
        border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
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
        containerColor = MaterialTheme.colorScheme.surface,
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
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            LocationType.values().forEach { loc ->
                LocationOptionItem(
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
fun LocationOptionItem(loc: LocationType, isSelected: Boolean, onClick: () -> Unit) {
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
        color = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent,
        border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(emoji, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Text(label, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            }
            if (isSelected) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF4ADE80))
            }
        }
    }
}

private enum class Brightness { Light, Dark }
private val ColorScheme.brightness: Brightness
    get() = if (surface.luminance() < 0.5f) Brightness.Dark else Brightness.Light

private fun Color.luminance(): Float {
    val red = if (red <= 0.03928f) red / 12.92f else Math.pow(((red + 0.055f) / 1.055f).toDouble(), 2.4).toFloat()
    val green = if (green <= 0.03928f) green / 12.92f else Math.pow(((green + 0.055f) / 1.055f).toDouble(), 2.4).toFloat()
    val blue = if (blue <= 0.03928f) blue / 12.92f else Math.pow(((blue + 0.055f) / 1.055f).toDouble(), 2.4).toFloat()
    return 0.2126f * red + 0.7152f * green + 0.0722f * blue
}
