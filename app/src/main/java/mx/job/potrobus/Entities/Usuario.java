package mx.job.potrobus.Entities;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import mx.job.potrobus.database.usuarioDB;

public class Usuario {
    private int id;
    private String nombre;
    private String username;
    private String correo;
    private String contrasena;
    private String telefono;

    public Usuario(String nombre, String username, String correo, String contrasena, String telefono) {
        this.nombre = nombre;
        this.username = username;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    public Usuario() {
    }

    public void signup(Context context, Usuario usuario){
        try{
            usuarioDB mayoDB = new usuarioDB(context, "usuarioDB", null, 1);
            SQLiteDatabase baseDatos = mayoDB.getReadableDatabase();
            baseDatos.execSQL("INSERT INTO usuario (nombre, telefono, correo, contrasena, username)" +
                    "VALUES('"+ usuario.getNombre() +"','"+ usuario.getTelefono() +"','"+ usuario.getCorreo() +"','"+usuario.getContrasena()+"','"+usuario.getUsername()+"')");
            baseDatos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Usuario login(Context context, String username, String contrasena){
        Usuario usuario = null;
        try {
            usuarioDB db = new usuarioDB(context, "usuarioDB", null, 1);
            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM usuario WHERE username='"+username+"' AND contrasena ='"+contrasena+"'", null);
            if (cursor.moveToFirst()){
                cursor.moveToFirst();
                usuario = new Usuario(cursor.getString(0), cursor.getString(1), cursor. getString(2), cursor.getString(3), cursor.getString(4));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return usuario;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getTelefono() {
        return telefono;
    }
}
