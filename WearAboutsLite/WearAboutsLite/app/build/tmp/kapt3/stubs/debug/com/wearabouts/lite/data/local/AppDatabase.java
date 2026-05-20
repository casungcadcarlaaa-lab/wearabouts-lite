package com.wearabouts.lite.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lcom/wearabouts/lite/data/local/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "clothingDao", "Lcom/wearabouts/lite/data/local/ClothingDao;", "historyDao", "Lcom/wearabouts/lite/data/local/HistoryDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.wearabouts.lite.data.local.ClothingItemEntity.class, com.wearabouts.lite.data.local.HistoryActivityEntity.class}, version = 3, exportSchema = false)
@androidx.room.TypeConverters(value = {com.wearabouts.lite.data.local.Converters.class})
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.wearabouts.lite.data.local.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.wearabouts.lite.data.local.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.wearabouts.lite.data.local.ClothingDao clothingDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.wearabouts.lite.data.local.HistoryDao historyDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\bB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/wearabouts/lite/data/local/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/wearabouts/lite/data/local/AppDatabase;", "getDatabase", "context", "Landroid/content/Context;", "DatabaseCallback", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wearabouts.lite.data.local.AppDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0016\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/wearabouts/lite/data/local/AppDatabase$Companion$DatabaseCallback;", "Landroidx/room/RoomDatabase$Callback;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "onCreate", "", "db", "Landroidx/sqlite/db/SupportSQLiteDatabase;", "populateDatabase", "clothingDao", "Lcom/wearabouts/lite/data/local/ClothingDao;", "(Lcom/wearabouts/lite/data/local/ClothingDao;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
        static final class DatabaseCallback extends androidx.room.RoomDatabase.Callback {
            @org.jetbrains.annotations.NotNull()
            private final android.content.Context context = null;
            
            public DatabaseCallback(@org.jetbrains.annotations.NotNull()
            android.content.Context context) {
                super();
            }
            
            @java.lang.Override()
            public void onCreate(@org.jetbrains.annotations.NotNull()
            androidx.sqlite.db.SupportSQLiteDatabase db) {
            }
            
            @org.jetbrains.annotations.Nullable()
            public final java.lang.Object populateDatabase(@org.jetbrains.annotations.NotNull()
            com.wearabouts.lite.data.local.ClothingDao clothingDao, @org.jetbrains.annotations.NotNull()
            kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
                return null;
            }
        }
    }
}