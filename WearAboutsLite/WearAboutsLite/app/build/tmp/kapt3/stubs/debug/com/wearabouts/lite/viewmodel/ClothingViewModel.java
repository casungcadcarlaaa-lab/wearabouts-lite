package com.wearabouts.lite.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0018\u0018\u00002\u00020\u0001:\u0001>B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\u0014J\u0006\u0010)\u001a\u00020\'J\u000e\u0010*\u001a\u00020\'2\u0006\u0010(\u001a\u00020\u0014J\u000e\u0010+\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010,J\u000e\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020\fJ\u000e\u0010/\u001a\u00020\'2\u0006\u00100\u001a\u00020\u001aJ\u000e\u00101\u001a\u00020\'2\u0006\u00102\u001a\u00020\tJ\u0010\u00103\u001a\u00020\'2\b\u00104\u001a\u0004\u0018\u00010\fJ\u000e\u00105\u001a\u00020\'2\u0006\u00106\u001a\u00020\fJ\u0010\u00107\u001a\u00020\'2\b\u00108\u001a\u0004\u0018\u00010\u000fJ\u000e\u00109\u001a\u00020\'2\u0006\u0010:\u001a\u00020\fJ\u0006\u0010;\u001a\u00020\'J\u0018\u0010<\u001a\u00020\'2\u0006\u0010(\u001a\u00020\u00142\b\u0010=\u001a\u0004\u0018\u00010\u0014R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00130\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u0019\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016R\u0019\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0016R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2 = {"Lcom/wearabouts/lite/viewmodel/ClothingViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/wearabouts/lite/data/repository/ClothingRepository;", "userPreferences", "Lcom/wearabouts/lite/data/local/UserPreferences;", "(Lcom/wearabouts/lite/data/repository/ClothingRepository;Lcom/wearabouts/lite/data/local/UserPreferences;)V", "_isDarkMode", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_isPrivateMode", "_profilePictureUri", "", "_searchQuery", "_statusFilter", "Lcom/wearabouts/lite/data/local/StatusType;", "_userName", "allItems", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/wearabouts/lite/data/model/ClothingItem;", "getAllItems", "()Lkotlinx/coroutines/flow/StateFlow;", "filteredClothes", "getFilteredClothes", "history", "Lcom/wearabouts/lite/data/model/HistoryActivity;", "getHistory", "isDarkMode", "isPrivateMode", "profilePictureUri", "getProfilePictureUri", "searchQuery", "getSearchQuery", "statusFilter", "getStatusFilter", "userName", "getUserName", "addItem", "", "item", "clearAllData", "deleteItem", "exportDataToJson", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "importDataFromJson", "json", "restoreFromHistory", "activity", "setPrivateMode", "enabled", "setProfilePictureUri", "uri", "setSearchQuery", "query", "setStatusFilter", "status", "setUserName", "name", "toggleDarkMode", "updateItem", "previousItem", "Factory", "app_debug"})
public final class ClothingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.wearabouts.lite.data.repository.ClothingRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wearabouts.lite.data.local.UserPreferences userPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.wearabouts.lite.data.model.ClothingItem>> allItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.wearabouts.lite.data.model.HistoryActivity>> history = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _userName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> userName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _profilePictureUri = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> profilePictureUri = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isDarkMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isDarkMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isPrivateMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPrivateMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wearabouts.lite.data.local.StatusType> _statusFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wearabouts.lite.data.local.StatusType> statusFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.wearabouts.lite.data.model.ClothingItem>> filteredClothes = null;
    
    public ClothingViewModel(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.repository.ClothingRepository repository, @org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.local.UserPreferences userPreferences) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.wearabouts.lite.data.model.ClothingItem>> getAllItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.wearabouts.lite.data.model.HistoryActivity>> getHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getProfilePictureUri() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isDarkMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPrivateMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wearabouts.lite.data.local.StatusType> getStatusFilter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.wearabouts.lite.data.model.ClothingItem>> getFilteredClothes() {
        return null;
    }
    
    public final void setUserName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void setProfilePictureUri(@org.jetbrains.annotations.Nullable()
    java.lang.String uri) {
    }
    
    public final void toggleDarkMode() {
    }
    
    public final void setPrivateMode(boolean enabled) {
    }
    
    public final void addItem(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.model.ClothingItem item) {
    }
    
    public final void updateItem(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.model.ClothingItem item, @org.jetbrains.annotations.Nullable()
    com.wearabouts.lite.data.model.ClothingItem previousItem) {
    }
    
    public final void deleteItem(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.model.ClothingItem item) {
    }
    
    public final void restoreFromHistory(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.model.HistoryActivity activity) {
    }
    
    public final void setSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void setStatusFilter(@org.jetbrains.annotations.Nullable()
    com.wearabouts.lite.data.local.StatusType status) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportDataToJson(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    public final boolean importDataFromJson(@org.jetbrains.annotations.NotNull()
    java.lang.String json) {
        return false;
    }
    
    public final void clearAllData() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J%\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\u000bH\u0016\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/wearabouts/lite/viewmodel/ClothingViewModel$Factory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "repository", "Lcom/wearabouts/lite/data/repository/ClothingRepository;", "userPreferences", "Lcom/wearabouts/lite/data/local/UserPreferences;", "(Lcom/wearabouts/lite/data/repository/ClothingRepository;Lcom/wearabouts/lite/data/local/UserPreferences;)V", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "app_debug"})
    public static final class Factory implements androidx.lifecycle.ViewModelProvider.Factory {
        @org.jetbrains.annotations.NotNull()
        private final com.wearabouts.lite.data.repository.ClothingRepository repository = null;
        @org.jetbrains.annotations.NotNull()
        private final com.wearabouts.lite.data.local.UserPreferences userPreferences = null;
        
        public Factory(@org.jetbrains.annotations.NotNull()
        com.wearabouts.lite.data.repository.ClothingRepository repository, @org.jetbrains.annotations.NotNull()
        com.wearabouts.lite.data.local.UserPreferences userPreferences) {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public <T extends androidx.lifecycle.ViewModel>T create(@org.jetbrains.annotations.NotNull()
        java.lang.Class<T> modelClass) {
            return null;
        }
    }
}