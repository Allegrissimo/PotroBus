package mx.job.potrobus.Entities;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.job.potrobus.database.ParadasDB;

public class Location {
    private int id;
    private double lat;
    private double lng;

    public Location(){

    }

    public Location(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public void insertarParada(Context context, Location location){
        try{
            ParadasDB mayoDB = new ParadasDB(context, "ParadasDB", null, 1);
            SQLiteDatabase baseDatos = mayoDB.getReadableDatabase();
            baseDatos.execSQL("INSERT INTO paradas (lat, lng)" +
                    "VALUES(" + location.getLat() + "," + location.getLng() + ")");
            baseDatos.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Location> getParadas(Context context){
        List<Location> paradas = new ArrayList<>();
        try{
            ParadasDB paradasDB = new ParadasDB(context, "ParadasDB", null, 1);
            SQLiteDatabase sqLiteDatabase = paradasDB.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT lat, lng FROM paradas", null);
            while (cursor.moveToNext()){
                Location l = new Location(cursor.getDouble(1), cursor.getDouble(2));
                paradas.add(l);
            }
            sqLiteDatabase.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return paradas;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
