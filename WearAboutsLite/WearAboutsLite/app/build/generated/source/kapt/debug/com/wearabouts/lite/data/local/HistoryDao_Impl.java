package com.wearabouts.lite.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class HistoryDao_Impl implements HistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HistoryActivityEntity> __insertionAdapterOfHistoryActivityEntity;

  private final Converters __converters = new Converters();

  private final SharedSQLiteStatement __preparedStmtOfDeleteActivity;

  private final SharedSQLiteStatement __preparedStmtOfClearAllActivities;

  public HistoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHistoryActivityEntity = new EntityInsertionAdapter<HistoryActivityEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `history_activities` (`id`,`itemId`,`itemName`,`itemEmoji`,`action`,`status`,`location`,`changes`,`previousStateJson`,`timestamp`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HistoryActivityEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getItemId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getItemId());
        }
        if (entity.getItemName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getItemName());
        }
        if (entity.getItemEmoji() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getItemEmoji());
        }
        final String _tmp = __converters.fromHistoryAction(entity.getAction());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        final String _tmp_1 = __converters.fromStatusType(entity.getStatus());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_1);
        }
        final String _tmp_2 = __converters.fromLocationType(entity.getLocation());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_2);
        }
        if (entity.getChanges() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getChanges());
        }
        if (entity.getPreviousStateJson() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPreviousStateJson());
        }
        statement.bindLong(10, entity.getTimestamp());
      }
    };
    this.__preparedStmtOfDeleteActivity = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM history_activities WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearAllActivities = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM history_activities";
        return _query;
      }
    };
  }

  @Override
  public Object insertActivity(final HistoryActivityEntity activity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfHistoryActivityEntity.insert(activity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteActivity(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteActivity.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteActivity.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAllActivities(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAllActivities.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAllActivities.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<HistoryActivityEntity>> getAllActivities() {
    final String _sql = "SELECT * FROM history_activities ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"history_activities"}, new Callable<List<HistoryActivityEntity>>() {
      @Override
      @NonNull
      public List<HistoryActivityEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "itemId");
          final int _cursorIndexOfItemName = CursorUtil.getColumnIndexOrThrow(_cursor, "itemName");
          final int _cursorIndexOfItemEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "itemEmoji");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfChanges = CursorUtil.getColumnIndexOrThrow(_cursor, "changes");
          final int _cursorIndexOfPreviousStateJson = CursorUtil.getColumnIndexOrThrow(_cursor, "previousStateJson");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<HistoryActivityEntity> _result = new ArrayList<HistoryActivityEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HistoryActivityEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpItemId;
            if (_cursor.isNull(_cursorIndexOfItemId)) {
              _tmpItemId = null;
            } else {
              _tmpItemId = _cursor.getString(_cursorIndexOfItemId);
            }
            final String _tmpItemName;
            if (_cursor.isNull(_cursorIndexOfItemName)) {
              _tmpItemName = null;
            } else {
              _tmpItemName = _cursor.getString(_cursorIndexOfItemName);
            }
            final String _tmpItemEmoji;
            if (_cursor.isNull(_cursorIndexOfItemEmoji)) {
              _tmpItemEmoji = null;
            } else {
              _tmpItemEmoji = _cursor.getString(_cursorIndexOfItemEmoji);
            }
            final HistoryAction _tmpAction;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfAction)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfAction);
            }
            _tmpAction = __converters.toHistoryAction(_tmp);
            final StatusType _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __converters.toStatusType(_tmp_1);
            final LocationType _tmpLocation;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfLocation);
            }
            _tmpLocation = __converters.toLocationType(_tmp_2);
            final String _tmpChanges;
            if (_cursor.isNull(_cursorIndexOfChanges)) {
              _tmpChanges = null;
            } else {
              _tmpChanges = _cursor.getString(_cursorIndexOfChanges);
            }
            final String _tmpPreviousStateJson;
            if (_cursor.isNull(_cursorIndexOfPreviousStateJson)) {
              _tmpPreviousStateJson = null;
            } else {
              _tmpPreviousStateJson = _cursor.getString(_cursorIndexOfPreviousStateJson);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new HistoryActivityEntity(_tmpId,_tmpItemId,_tmpItemName,_tmpItemEmoji,_tmpAction,_tmpStatus,_tmpLocation,_tmpChanges,_tmpPreviousStateJson,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getActivityById(final String id,
      final Continuation<? super HistoryActivityEntity> $completion) {
    final String _sql = "SELECT * FROM history_activities WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HistoryActivityEntity>() {
      @Override
      @Nullable
      public HistoryActivityEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "itemId");
          final int _cursorIndexOfItemName = CursorUtil.getColumnIndexOrThrow(_cursor, "itemName");
          final int _cursorIndexOfItemEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "itemEmoji");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfChanges = CursorUtil.getColumnIndexOrThrow(_cursor, "changes");
          final int _cursorIndexOfPreviousStateJson = CursorUtil.getColumnIndexOrThrow(_cursor, "previousStateJson");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final HistoryActivityEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpItemId;
            if (_cursor.isNull(_cursorIndexOfItemId)) {
              _tmpItemId = null;
            } else {
              _tmpItemId = _cursor.getString(_cursorIndexOfItemId);
            }
            final String _tmpItemName;
            if (_cursor.isNull(_cursorIndexOfItemName)) {
              _tmpItemName = null;
            } else {
              _tmpItemName = _cursor.getString(_cursorIndexOfItemName);
            }
            final String _tmpItemEmoji;
            if (_cursor.isNull(_cursorIndexOfItemEmoji)) {
              _tmpItemEmoji = null;
            } else {
              _tmpItemEmoji = _cursor.getString(_cursorIndexOfItemEmoji);
            }
            final HistoryAction _tmpAction;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfAction)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfAction);
            }
            _tmpAction = __converters.toHistoryAction(_tmp);
            final StatusType _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __converters.toStatusType(_tmp_1);
            final LocationType _tmpLocation;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfLocation);
            }
            _tmpLocation = __converters.toLocationType(_tmp_2);
            final String _tmpChanges;
            if (_cursor.isNull(_cursorIndexOfChanges)) {
              _tmpChanges = null;
            } else {
              _tmpChanges = _cursor.getString(_cursorIndexOfChanges);
            }
            final String _tmpPreviousStateJson;
            if (_cursor.isNull(_cursorIndexOfPreviousStateJson)) {
              _tmpPreviousStateJson = null;
            } else {
              _tmpPreviousStateJson = _cursor.getString(_cursorIndexOfPreviousStateJson);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _result = new HistoryActivityEntity(_tmpId,_tmpItemId,_tmpItemName,_tmpItemEmoji,_tmpAction,_tmpStatus,_tmpLocation,_tmpChanges,_tmpPreviousStateJson,_tmpTimestamp);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
