package com.wearabouts.lite.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0015J\u000e\u0010\u0016\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0015J\u000e\u0010\u0019\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001c\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010 \u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0015J \u0010!\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000e2\b\u0010\"\u001a\u0004\u0018\u00010\u000eH\u0086@\u00a2\u0006\u0002\u0010#J\f\u0010$\u001a\u00020\u000e*\u00020%H\u0002J\f\u0010$\u001a\u00020\n*\u00020&H\u0002J\f\u0010\'\u001a\u00020%*\u00020\u000eH\u0002R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/wearabouts/lite/data/repository/ClothingRepository;", "", "clothingDao", "Lcom/wearabouts/lite/data/local/ClothingDao;", "historyDao", "Lcom/wearabouts/lite/data/local/HistoryDao;", "(Lcom/wearabouts/lite/data/local/ClothingDao;Lcom/wearabouts/lite/data/local/HistoryDao;)V", "allHistory", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/wearabouts/lite/data/model/HistoryActivity;", "getAllHistory", "()Lkotlinx/coroutines/flow/Flow;", "allItems", "Lcom/wearabouts/lite/data/model/ClothingItem;", "getAllItems", "gson", "Lcom/google/gson/Gson;", "addItem", "", "item", "(Lcom/wearabouts/lite/data/model/ClothingItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearAllData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteItem", "exportDataToJson", "", "getItemById", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "importDataFromJson", "json", "restoreItem", "updateItem", "previousItem", "(Lcom/wearabouts/lite/data/model/ClothingItem;Lcom/wearabouts/lite/data/model/ClothingItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toDomain", "Lcom/wearabouts/lite/data/local/ClothingItemEntity;", "Lcom/wearabouts/lite/data/local/HistoryActivityEntity;", "toEntity", "app_debug"})
public final class ClothingRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.wearabouts.lite.data.local.ClothingDao clothingDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wearabouts.lite.data.local.HistoryDao historyDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.wearabouts.lite.data.model.ClothingItem>> allItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.wearabouts.lite.data.model.HistoryActivity>> allHistory = null;
    
    public ClothingRepository(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.local.ClothingDao clothingDao, @org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.local.HistoryDao historyDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.wearabouts.lite.data.model.ClothingItem>> getAllItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.wearabouts.lite.data.model.HistoryActivity>> getAllHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addItem(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.model.ClothingItem item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateItem(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.model.ClothingItem item, @org.jetbrains.annotations.Nullable()
    com.wearabouts.lite.data.model.ClothingItem previousItem, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteItem(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.model.ClothingItem item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object restoreItem(@org.jetbrains.annotations.NotNull()
    com.wearabouts.lite.data.model.ClothingItem item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getItemById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wearabouts.lite.data.model.ClothingItem> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportDataToJson(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object importDataFromJson(@org.jetbrains.annotations.NotNull()
    java.lang.String json, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearAllData(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.wearabouts.lite.data.model.ClothingItem toDomain(com.wearabouts.lite.data.local.ClothingItemEntity $this$toDomain) {
        return null;
    }
    
    private final com.wearabouts.lite.data.local.ClothingItemEntity toEntity(com.wearabouts.lite.data.model.ClothingItem $this$toEntity) {
        return null;
    }
    
    private final com.wearabouts.lite.data.model.HistoryActivity toDomain(com.wearabouts.lite.data.local.HistoryActivityEntity $this$toDomain) {
        return null;
    }
}