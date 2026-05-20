package com.wearabouts.lite.ui.screens.itemdetail;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a9\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u0011\u0010\f\u001a\r\u0012\u0004\u0012\u00020\u00060\u000b\u00a2\u0006\u0002\b\rH\u0007\u001a:\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00060\u0014H\u0007\u001a&\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u0007\u001a2\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00172\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00060\u0014H\u0007\u001a\u0010\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020 H\u0007\u001a&\u0010!\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u0007\u001a2\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020 2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00060\u0014H\u0007\u001a\u0010\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020&H\u0002\u001a\u0016\u0010\'\u001a\u00020(*\u00020)H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b*\u0010+\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006,"}, d2 = {"brightness", "Lcom/wearabouts/lite/ui/screens/itemdetail/Brightness;", "Landroidx/compose/material3/ColorScheme;", "getBrightness", "(Landroidx/compose/material3/ColorScheme;)Lcom/wearabouts/lite/ui/screens/itemdetail/Brightness;", "DetailSectionCard", "", "label", "", "buttonText", "onClick", "Lkotlin/Function0;", "content", "Landroidx/compose/runtime/Composable;", "ItemDetailScreen", "itemId", "viewModel", "Lcom/wearabouts/lite/viewmodel/ClothingViewModel;", "onNavigateBack", "onNavigateToEdit", "Lkotlin/Function1;", "LocationOptionItem", "loc", "Lcom/wearabouts/lite/data/local/LocationType;", "isSelected", "", "LocationUpdateSheet", "currentLocation", "onDismiss", "onSave", "StatusIndicator", "status", "Lcom/wearabouts/lite/data/local/StatusType;", "StatusOptionItem", "StatusUpdateSheet", "currentStatus", "formatLastUpdated", "timestamp", "", "luminance", "", "Landroidx/compose/ui/graphics/Color;", "luminance-8_81llA", "(J)F", "app_debug"})
public final class ItemDetailScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ItemDetailScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.viewmodel.ClothingViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToEdit) {
    }
    
    private static final java.lang.String formatLastUpdated(long timestamp) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DetailSectionCard(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String buttonText, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StatusIndicator(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.local.StatusType status) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void StatusUpdateSheet(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.local.StatusType currentStatus, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.wearabouts.lite.data.local.StatusType, kotlin.Unit> onSave) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StatusOptionItem(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.local.StatusType status, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void LocationUpdateSheet(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.local.LocationType currentLocation, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.wearabouts.lite.data.local.LocationType, kotlin.Unit> onSave) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LocationOptionItem(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.local.LocationType loc, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    private static final com.wearabouts.lite.ui.screens.itemdetail.Brightness getBrightness(androidx.compose.material3.ColorScheme $this$brightness) {
        return null;
    }
}