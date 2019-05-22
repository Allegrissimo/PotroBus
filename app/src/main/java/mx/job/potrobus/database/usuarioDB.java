package mx.job.potrobus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class usuarioDB extends SQLiteOpenHelper {

    public usuarioDB(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, username TEXT, correo TEXT, contrasena TEXT, telefono TEXT)");
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
