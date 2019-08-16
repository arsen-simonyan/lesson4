package am.newway.lesson4.data;

import am.newway.lesson4.data.variable.Var;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.annotation.NonNull;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Settings
{
    private final String MY_PREFS_NAME = "Tasks";
    private static volatile Settings instance;
    private static Context context;
    private SQLite sqlite;

    public boolean isReadContactsAccess() {
        return isReadContactsAccess;
    }

    public void setReadContactsAccess(boolean readContactsAccess) {
        isReadContactsAccess = readContactsAccess;
    }

    private boolean isReadContactsAccess;

    public static Settings getInstance( @NonNull Context ctx) {
        context = ctx;
        Settings localInstance = instance;
        if (localInstance == null) {
            synchronized (Settings.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Settings();
                }
            }
        }
        return localInstance;
    }

    public SQLite SQL( ) {
        SQLite sql = sqlite;
        if (sql == null) {
            synchronized (SQLite.class) {
                sql = sqlite;
                if (sql == null) {
                    sqlite = sql = new SQLite(context, null);
                }
            }
        }
        return sql;
    }

    private void addNewCard(Tasks task)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        editor.putString( Var._Name, task.getStrName(  ) );
        editor.putString(Var._Description, task.getStrDescription(  ));
        editor.putString(Var._Uri, task.getUriImage( ).toString() );
        editor.putString(Var._Type, task.getStrType(  ));
        editor.apply();
    }

    private List<Tasks> getCards()
    {

        SharedPreferences pref = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        new Tasks(
        pref.getString(Var._Name, ""),
        pref.getString(Var._Description, ""),
                Uri.parse(pref.getString(Var._Uri,  "")),
        pref.getString(Var._Type, ""));

        return null;
    }
}
