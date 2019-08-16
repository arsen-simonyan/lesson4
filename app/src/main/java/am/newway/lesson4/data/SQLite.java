package am.newway.lesson4.data;

import am.newway.lesson4.data.variable.Var;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SQLite extends SQLiteOpenHelper
{
    private final String tag = getClass().getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private final ContentValues values;

    SQLite( Context context , SQLiteDatabase.CursorFactory factory )
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        values = new ContentValues();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(String.format( Locale.getDefault(), "CREATE TABLE IF NOT EXISTS Tasks (" +
                        "%s INTEGER PRIMARY KEY, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT);",
                Var._ID, Var._Name, Var._Description, Var._Uri, Var._Type)); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public void addValue(String strKey, String strValue)
    {
        values.put(strKey, strValue);
    }

    public void insertDB(String strTable)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(strTable, null, values);
        clearDB(db);
    }

    private void clearDB(SQLiteDatabase db)
    {
        values.clear();
        db.close();
    }

    public List<Tasks> getTasks( String query)
    {
        Log.d(tag, "getTasks");
        List<Tasks> task = new ArrayList<>(  );

        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery(query, null);

        if (cur != null && cur.getCount() > 0)
        {
            cur.moveToFirst();
            do
            {
                Uri uri = Uri.parse(cur.getString(cur.getColumnIndex(Var._Uri)));
                task.add( new Tasks(
                        cur.getString(cur.getColumnIndex(Var._Name)),
                        cur.getString(cur.getColumnIndex(Var._Description)),
                        uri,
                        cur.getString(cur.getColumnIndex(Var._Type))));
            }while (cur.moveToNext());
            cur.close();
        } else Log.d(tag, "Cursor is null");
        clearDB(db);
        return task;
    }
}
