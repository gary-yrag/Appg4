package com.example.inicio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "local";

    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        * Estado => {Pedido,Entregado,Leido,Omitido}*/

        String tbmuestra = "CREATE TABLE IF NOT EXISTS muestra" +
                "(id VARCHAR, " +
                "hash VARCHAR, " +
                "oracion VARCHAR, " +
                "hash_padre VARCHAR, " +
                "hash_hijo VARCHAR, " +
                "nick_hijo VARCHAR, " +
                "porcentaje_riesgo VARCHAR, " +
                "estado VARCHAR," +
                "tiempo_creado VARCHAR," +
                "tiempo_pedido VARCHAR, " +
                "tiempo_entregado VARCHAR," +
                "tiempo_leido VARCHAR," +
                "tiempo_omitido VARCHAR," +
                "tiempo_estadoactual VARCHAR)";

        db.execSQL(tbmuestra);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS muestra");// Drop older table if existed
        onCreate(db);// Creating tables again
    }
    public Cursor onCursor(String sql){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public void onInsert(String table, ContentValues value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table,null,value);
    }

    public void onUpdate(String table,String where,String[] where_val, ContentValues value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(table, value, where, where_val);
    }

    public void onDelete(String sql, String where,String[] value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(sql,where,value);
    }
}
