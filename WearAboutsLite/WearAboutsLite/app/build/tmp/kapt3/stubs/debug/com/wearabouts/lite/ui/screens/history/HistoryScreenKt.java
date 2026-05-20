package com.wearabouts.lite.ui.screens.history;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0018\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u001a \u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0007\u001a\u0010\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0013H\u0007\u001a\u000e\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016\u00a8\u0006\u0017"}, d2 = {"DetailedHistoryCard", "", "activity", "Lcom/wearabouts/lite/data/model/HistoryActivity;", "onRestore", "Lkotlin/Function0;", "HistoryScreen", "viewModel", "Lcom/wearabouts/lite/viewmodel/ClothingViewModel;", "navController", "Landroidx/navigation/NavController;", "InfoSection", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "label", "", "value", "StatusIndicatorSmall", "status", "Lcom/wearabouts/lite/data/local/StatusType;", "getTimeAgo", "time", "", "app_debug"})
public final class HistoryScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HistoryScreen(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.viewmodel.ClothingViewModel viewModel, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DetailedHistoryCard(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.model.HistoryActivity activity, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRestore) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void InfoSection(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StatusIndicatorSmall(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.local.StatusType status) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getTimeAgo(long time) {
        return null;
    }
}