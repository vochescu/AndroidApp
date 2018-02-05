package ro.ase.eu.proiect.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.networking.HttpConnection;
import ro.ase.eu.proiect.networking.HttpResponse;
import ro.ase.eu.proiect.util.Line;
import ro.ase.eu.proiect.util.SearchLineAdapter;

public class DatabaseController extends SQLiteOpenHelper implements DatabaseConstants{

    private static DatabaseController controller = null;
    public DatabaseController(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        try {
            database.execSQL(CREATE_TABLE_FAVORITELINE);
            database.execSQL(CREATE_TABLE_LINE);
            database.execSQL(CREATE_TABLE_STOP);
            database.execSQL(CREATE_TABLE_ROUTESTOP);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int o, int n) {
        try {
            database.execSQL(DROP_TABLE_ROUTESTOP);
            database.execSQL(DROP_TABLE_LINE);
            database.execSQL(DROP_TABLE_STOP);
            database.execSQL(DROP_TABLE_FAVORITELINE);
            onCreate(database);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static DatabaseController getInstance(Context context){
        synchronized (DatabaseController.class){
            if(controller == null){
                controller = new DatabaseController(context);
            }
        }
        return controller;
    }
}
