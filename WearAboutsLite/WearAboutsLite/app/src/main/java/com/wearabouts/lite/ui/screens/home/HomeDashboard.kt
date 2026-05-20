package com.wearabouts.lite.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wearabouts.lite.data.local.StatusType
import com.wearabouts.lite.navigation.Screen
import com.wearabouts.lite.ui.components.BottomNavBar
import com.wearabouts.lite.ui.components.ClothingCard
import com.wearabouts.lite.ui.components.bounceClick
import com.wearabouts.lite.ui.theme.Primary
import com.wearabouts.lite.ui.theme.TextSecondary
import com.wearabouts.lite.viewmodel.ClothingViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDashboard(
    viewModel: ClothingViewModel,
    navController: NavHostController
) {
    val clothes by viewModel.allItems.collectAsState()
    val userName by viewModel.userName.collectAsState()
    var selectedStatus by remember { mutableStateOf<StatusType?>(null) }
    var showFilters by remember { mutableStateOf(false) }

    val filteredClothes = remember(clothes, selectedStatus) {
        if (selectedStatus == null) clothes else clothes.filter { it.status == selectedStatus }
    }

    val clean = clothes.count { it.status == StatusType.Clean }
    val used = clothes.count { it.status == StatusType.Used }
    val borrowed = clothes.count { it.status == StatusType.Borrowed }
    val dirty = clothes.count { it.status == StatusType.Dirty }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "HI, ${userName.uppercase(Locale.getDefault())} 👋",
                        modifier = Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp,
                            letterSpacing = (-0.5).sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddItem.route) },
                containerColor = Primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.bounceClick()
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Stats Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SummaryCard(color = Color(0xFF4ADE80), label = "Clean", count = clean, modifier = Modifier.weight(1f))
                SummaryCard(color = Color(0xFFFBBF24), label = "Used", count = used, modifier = Modifier.weight(1f))
                SummaryCard(color = Color(0xFF60A5FA), label = "Borrowed", count = borrowed, modifier = Modifier.weight(1f))
                SummaryCard(color = Color(0xFF92400E), label = "Dirty", count = dirty, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // My Clothes row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Clothes",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                
                IconButton(onClick = { showFilters = !showFilters }) {
                    Icon(
                        imageVector = Icons.Outlined.FilterAlt,
                        contentDescription = "Filter",
                        tint = Primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // Collapsible Filter Row
            AnimatedVisibility(visible = showFilters) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    StatusFilterRow(
                        selectedStatus = selectedStatus,
                        onStatusSelected = { selectedStatus = it }
                    )
                    if (selectedStatus != null) {
                        TextButton(
                            onClick = { selectedStatus = null },
                            modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                        ) {
                            Text("Clear Filter", color = Primary, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredClothes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().padding(bottom = 50.dp), contentAlignment = Alignment.Center) {
                    Text("No clothes found", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(filteredClothes) { item ->
                        ClothingCard(
                            item = item,
                            onClick = { navController.navigate(Screen.ItemDetail.createRoute(item.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryCard(color: Color, label: String, count: Int, modifier: Modifier = Modifier) {
    val animatedCount by animateIntAsState(
        targetValue = count,
        animationSpec = tween(durationMillis = 1000),
        label = "countAnimation"
    )

    Card(
        modifier = modifier.height(200.dp),
        shape = RoundedCornerShape(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.9f),
                                color.copy(alpha = 0.8f),
                                color
                            ),
                            center = Offset(15f, 15f),
                            radius = 80f
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Color.White.copy(alpha = 0.4f), Color.Transparent),
                                center = Offset(10f, 10f),
                                radius = 40f
                            )
                        )
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = animatedCount.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        fontFamily = FontFamily.Serif,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.1f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun StatusFilterRow(
    selectedStatus: StatusType?,
    onStatusSelected: (StatusType?) -> Unit
) {
    val statuses = StatusType.entries.toTypedArray()
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        statuses.forEach { status ->
            val isSelected = selectedStatus == status
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .bounceClick()
                    .clickable { onStatusSelected(if (isSelected) null else status) },
                color = if (isSelected) Primary else MaterialTheme.colorScheme.surfaceVariant,
                border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = status.name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}
