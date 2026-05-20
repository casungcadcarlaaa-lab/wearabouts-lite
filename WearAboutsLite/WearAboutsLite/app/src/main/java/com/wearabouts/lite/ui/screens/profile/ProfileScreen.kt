package com.wearabouts.lite.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.wearabouts.lite.data.local.LocationType
import com.wearabouts.lite.data.local.StatusType
import com.wearabouts.lite.ui.components.BottomNavBar
import com.wearabouts.lite.ui.components.bounceClick
import com.wearabouts.lite.ui.theme.*
import com.wearabouts.lite.util.FileUtil
import com.wearabouts.lite.viewmodel.ClothingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ClothingViewModel,
    navController: NavController,
    onNavigateBack: () -> Unit,
    onSignOut: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val context = LocalContext.current
    val items by viewModel.allItems.collectAsState()
    val history by viewModel.history.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val profilePictureUri by viewModel.profilePictureUri.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val savedUri = FileUtil.saveImageToInternalStorage(context, it)
            viewModel.setProfilePictureUri(savedUri?.toString())
        }
    }

    // Calculations for Wardrobe Overview
    val totalItems = items.size
    val cleanCount = items.count { it.status == StatusType.Clean }
    val activitiesCount = history.size
    val topLocation = if (items.isEmpty()) "None" else {
        items.groupBy { it.location }
            .maxByOrNull { it.value.size }
            ?.key?.let { loc ->
                when(loc) {
                    LocationType.Cabinet -> "Cabinet"
                    LocationType.LaundryBasket -> "Laundry Basket"
                    LocationType.CurrentlyWearing -> "Wearing"
                    LocationType.Borrowed -> "Borrowed"
                }
            } ?: "None"
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Profile",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Serif,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
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

            // Avatar with Camera Overlay
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Primary)
                        .clickable { photoLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (profilePictureUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(profilePictureUri),
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(
                            text = userName.take(1).uppercase(),
                            color = Color.White,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Surface(
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 4.dp,
                    onClick = { photoLauncher.launch("image/*") }
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = "Change Photo", tint = TextSecondary, modifier = Modifier.size(20.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = "${userName.lowercase().replace(" ", ".")}@unc.edu.ph",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Wardrobe Overview Section
            SectionTitle("Wardrobe Overview")
            
            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OverviewCard(
                        icon = "🧺",
                        label = "Total Items",
                        value = totalItems.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    OverviewCard(
                        icon = "✨",
                        label = "Clean",
                        value = cleanCount.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OverviewCard(
                        iconNode = {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF60A5FA)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Sync, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            }
                        },
                        label = "Activities",
                        value = activitiesCount.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    OverviewCard(
                        icon = "🏠",
                        label = "Top Location",
                        value = topLocation,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Categories Section
            SectionTitle("Categories")
            
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val categories = listOf(
                        "Tops" to "👕",
                        "Bottoms" to "👖",
                        "Outerwear" to "🧥",
                        "Accessories" to "🧣",
                        "Footwear" to "👟",
                        "Underwear" to "🩲"
                    )
                    categories.forEachIndexed { index, pair ->
                        CategoryItem(
                            label = pair.first,
                            emoji = pair.second,
                            count = items.count { it.category.contains(pair.first, ignoreCase = true) }
                        )
                        if (index < categories.size - 1) Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            SectionTitle("Settings")
            
            Spacer(modifier = Modifier.height(16.dp))

            // Settings Group with individual borders
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SettingsItem(
                    icon = Icons.Outlined.Settings, 
                    label = "App Settings",
                    onClick = onNavigateToSettings
                )
                SettingsItem(
                    icon = if (isDarkMode) Icons.Default.LightMode else Icons.Outlined.DarkMode, 
                    label = if (isDarkMode) "Light Mode" else "Dark Mode",
                    onClick = { viewModel.toggleDarkMode() }
                )
                SettingsItem(
                    icon = Icons.Default.Logout,
                    label = "Log Out",
                    labelColor = Color.Red,
                    onClick = onSignOut
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun OverviewCard(
    icon: String? = null,
    iconNode: @Composable (() -> Unit)? = null,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(130.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (iconNode != null) {
                iconNode()
            } else if (icon != null) {
                Text(text = icon, fontSize = 24.sp)
            }
            
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.5.sp,
            fontSize = 18.sp
        ),
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CategoryItem(label: String, emoji: String, count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label, 
                style = MaterialTheme.typography.bodyLarge, 
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = emoji, fontSize = 18.sp)
        }
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = count.toString(), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    label: String,
    labelColor: Color? = null,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().bounceClick(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp, 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon, 
                contentDescription = null, 
                tint = if(labelColor == Color.Red) Color.Red else Primary, 
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label, 
                style = MaterialTheme.typography.titleMedium, 
                fontWeight = FontWeight.Bold, 
                color = labelColor ?: MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
