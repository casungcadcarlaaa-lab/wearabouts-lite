package com.wearabouts.lite.ui.screens.additem

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.wearabouts.lite.data.local.LocationType
import com.wearabouts.lite.data.local.StatusType
import com.wearabouts.lite.data.model.ClothingItem
import com.wearabouts.lite.ui.components.InputField
import com.wearabouts.lite.ui.components.PrimaryButton
import com.wearabouts.lite.ui.theme.*
import com.wearabouts.lite.util.FileUtil
import com.wearabouts.lite.viewmodel.ClothingViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    viewModel: ClothingViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Tops 👕") }
    var status by remember { mutableStateOf(StatusType.Clean) }
    var location by remember { mutableStateOf(LocationType.Cabinet) }
    var notes by remember { mutableStateOf("") }
    var borrowedBy by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var isCategoryExpanded by remember { mutableStateOf(false) }

    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val savedUri = FileUtil.saveImageToInternalStorage(context, it)
            photoUri = savedUri
        }
    }

    val categories = listOf(
        "Tops 👕", "Bottoms 👖", "Outerwear 🧥", "Accessories 🧣", "Footwear 👟", "Underwear 🩲"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Add Clothing", 
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    ) 
                },
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
                .fillMaxSize()
                .padding(padding)
        ) {
            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Item Name
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Item Name", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    InputField(
                        value = name,
                        onValueChange = { name = it },
                        label = "",
                        placeholder = "White Polo Shirt"
                    )
                }

                // Category
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Category", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    ExposedDropdownMenuBox(
                        expanded = isCategoryExpanded,
                        onExpandedChange = { isCategoryExpanded = !isCategoryExpanded }
                    ) {
                        OutlinedTextField(
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryExpanded)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                                focusedBorderColor = Color.LightGray,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = isCategoryExpanded,
                            onDismissRequest = { isCategoryExpanded = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                        ) {
                            categories.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat, color = MaterialTheme.colorScheme.onSurface) },
                                    onClick = {
                                        category = cat
                                        isCategoryExpanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }

                // Status
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Status", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            StatusChip(
                                label = "Clean",
                                dotColor = Color(0xFF4ADE80),
                                isSelected = status == StatusType.Clean,
                                onClick = { status = StatusType.Clean }
                            )
                        }
                        item {
                            StatusChip(
                                label = "Used",
                                dotColor = Color(0xFFFACC15),
                                isSelected = status == StatusType.Used,
                                onClick = { status = StatusType.Used }
                            )
                        }
                        item {
                            StatusChip(
                                label = "Borrowed",
                                dotColor = Color(0xFF60A5FA),
                                isSelected = status == StatusType.Borrowed,
                                onClick = { status = StatusType.Borrowed }
                            )
                        }
                        item {
                            StatusChip(
                                label = "Dirty",
                                dotColor = Color(0xFFF87171),
                                isSelected = status == StatusType.Dirty,
                                onClick = { status = StatusType.Dirty }
                            )
                        }
                    }
                }

                // Location
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Location", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            LocationChip(
                                label = "Cabinet",
                                icon = "🗄️",
                                isSelected = location == LocationType.Cabinet,
                                onClick = { location = LocationType.Cabinet }
                            )
                        }
                        item {
                            LocationChip(
                                label = "Laundry Basket",
                                icon = "🧺",
                                isSelected = location == LocationType.LaundryBasket,
                                onClick = { location = LocationType.LaundryBasket }
                            )
                        }
                        item {
                            LocationChip(
                                label = "Currently Wearing",
                                icon = "👕",
                                isSelected = location == LocationType.CurrentlyWearing,
                                onClick = { location = LocationType.CurrentlyWearing }
                            )
                        }
                        item {
                            LocationChip(
                                label = "Borrowed",
                                icon = "🤝",
                                isSelected = location == LocationType.Borrowed,
                                onClick = { location = LocationType.Borrowed }
                            )
                        }
                    }
                }

                // Notes
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Notes (optional)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    InputField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = "",
                        placeholder = "Add any additional details...",
                        singleLine = false,
                        modifier = Modifier.height(100.dp)
                    )
                }

                // Borrowed By
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Borrowed By (optional)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    InputField(
                        value = borrowedBy,
                        onValueChange = { borrowedBy = it },
                        label = "",
                        placeholder = "Who borrowed this item?"
                    )
                }

                // Photo
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Photo (optional)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .border(BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)), RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable { photoLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (photoUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(photoUri),
                                contentDescription = "Selected Photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Default.CameraAlt, 
                                    contentDescription = null, 
                                    tint = TextSecondary,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Add Photo", fontSize = 12.sp, color = TextSecondary)
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Fixed Bottom Bar with Save Button
            Surface(
                tonalElevation = 4.dp,
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    PrimaryButton(
                        text = "Save Item",
                        onClick = {
                            val newItem = ClothingItem(
                                id = UUID.randomUUID().toString(),
                                name = name,
                                emoji = category.split(" ").last(),
                                category = category.split(" ").first(),
                                status = status,
                                location = location,
                                notes = notes.takeIf { it.isNotBlank() },
                                borrowedBy = borrowedBy.takeIf { it.isNotBlank() },
                                photoUri = photoUri?.toString(),
                                lastUpdated = System.currentTimeMillis()
                            )
                            viewModel.addItem(newItem)
                            onNavigateBack()
                        },
                        enabled = name.isNotBlank()
                    )
                }
            }
        }
    }
}

@Composable
fun StatusChip(
    label: String,
    dotColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(999.dp),
        color = if (isSelected) Primary else MaterialTheme.colorScheme.surface,
        border = if (isSelected) null else BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
        modifier = modifier.height(44.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(dotColor)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun LocationChip(
    label: String,
    icon: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(999.dp),
        color = if (isSelected) Primary else MaterialTheme.colorScheme.surface,
        border = if (isSelected) null else BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
        modifier = Modifier.height(44.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = icon, fontSize = 16.sp)
            Text(
                text = label,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 14.sp
            )
        }
    }
}
