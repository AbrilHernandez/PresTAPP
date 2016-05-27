package com.example.abril.prestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import java.beans.IndexedPropertyChangeEvent;
import java.util.Vector;

import www.ittepic.edu.mx.prestapp.DB_Help;

public class DBManager {
//nombre tabla
public static final String NAME_TABLE1 = "registros";
    public static final String NAME_TABLE2 = "categorias";
    public static final String NAME_TABLE3 = "devoluciones";
    //campos
    public static final String ID = "id";
    public static final String CATEGORIA = "categoria";
    public static final String ARTICULO = "articulo";
    public static final String NAME = "nombre";
    public static final String FECHA_PRESTAMO = "fechaPrestamo";
    public static final String FECHA_ENTREGA = "fechaEntrega";
    public static final String ID2 = "id";
    public static final String NAME_CATEGORIA = "categoria";
    public static final String ID3 = "id";
    public static final String NAME_ARTICULO = "objeto";
    public static final String NAME_PRESTATARIO = "nombre";
    public static final String FECHA_REGRESO = "recuperado";

    //sentencia crear tabla
    public static final String CREATE_TABLE = "create table " + NAME_TABLE1 + "("
            + ID + " integer primary key autoincrement,"
            + CATEGORIA + " text not null,"
            + ARTICULO + " text not null,"
            + NAME + " text not null," +
            FECHA_PRESTAMO + " date not null,"
            + FECHA_ENTREGA + " date not null);";

    public static final String CREATE_TABLE2 = "create table " + NAME_TABLE2 + " ("
            + ID2 + " integer primary key autoincrement,"
            + NAME_CATEGORIA + " text not null);";

    public static final String CREATE_TABLE3 = "create table " + NAME_TABLE3 + " ("
            + ID3 + " integer primary key autoincrement,"
            + NAME_ARTICULO + " text not null,"
            + NAME_PRESTATARIO + " text not null,"
            + FECHA_REGRESO + " date not null);";

            //creamos los objetos helper y sqlitedatabase para la utilizacion de la base de datos
            private DB_Help helper;
    private SQLiteDatabase db;
    public DBManager(Context contexto) {
        helper = new DB_Help(contexto);//mediante este objeto podremos acceder a sentencias al crear una instancia
        db = helper.getWritableDatabase(); //instanciamos y obtenemos la bd en modo escritura
        }//constructor
        private void modoEscritura() {
            db = helper.getWritableDatabase(); }
            //poner el db en modo escritura
            private void modoLectura() { db = helper.getReadableDatabase(); }
    //poner el db en modo lectura
    /private ContentValues getValores(String objeto, String especif, String name, String fechaP, String fechaE) {

    //put(Indentificador o nombre del campo, valor)
    ContentValues valores = new ContentValues();
        valores.put(CATEGORIA, objeto);
        valores.put(ARTICULO, especif);
        valores.put(NAME, name);
        valores.put(FECHA_PRESTAMO, fechaP);
        valores.put(FECHA_ENTREGA, fechaE);

        return valores; }//retornar los valores a insertar dentro de un objeto ContentValues

    public boolean insertarDevolucion(String objeto, String nombre, String fecha) {
        String sql = "insert into " + NAME_TABLE3 + " (" + NAME_ARTICULO + "," + NAME_PRESTATARIO + "," + FECHA_REGRESO + ") values('" + objeto + "','" + nombre + "','" + fecha + "')";
        try {
            modoEscritura();
            db.execSQL(sql);
            db.close();
            return true;
        }
        catch (SQLiteException e) {
            return false; }
    }//insertar devolucion
    public boolean insertarPrestamo(String objeto, String especif, String name, String fechaP, String fechaE) {
        try {
            modoEscritura();
            db.insert(NAME_TABLE1, null, getValores(objeto, especif, name, fechaP, fechaE));
            db.close();
            return true;
        } catch (Exception e) {
            return false; }
    }//insertar Prestamo
    public boolean insertarTipo(String nombre) {
        try {
            modoEscritura();
            db.execSQL("insert into " + NAME_TABLE2 + " (" + NAME_CATEGORIA + ") values('" + nombre + "');");
            db.close();
            return true;
        }
        catch (Exception e) {
            return false; }
    }//insertar nuevo objeto
    public boolean eliminarPrestamo(int i) {
        try { modoEscritura();
            db.execSQL("delete from registros where " + ID + " = " + i + ";");
            db.close();
            return true;
        } catch (Exception e) {
            return false; } }

            //eliminar prestamo
            public boolean eliminarTipo(String name) {
                try { //delete(tabla,condicion where,valores en vecctor)
                modoEscritura();
                    db.delete(NAME_TABLE2, NAME_CATEGORIA + " =?", new String[]{name});

                    //se le pasa un venctor de parametro por si son varios datos a eliminar
                    db.close();
                    return true;
                }
                catch (Exception e) {
                    return false; }
            }//eliminar tipo de objeto

            public boolean eliminarDevolucion(int i) {
                try { modoEscritura();
                    db.execSQL("delete from devoluciones where " + ID3 + " = " + i + ";");
                    db.close(); return true; }
                catch (Exception e) {
                    return false; }
            }//eliminar prestamo

            public boolean modificarPrestamo(String id, String objeto, String especif, String name, String fechaP, String fechaE) {
                try { //update (tabla,valores,condicion wherer)
                modoEscritura();
                db.update(NAME_TABLE1, getValores(objeto, especif, name, fechaP, fechaE), ID + "= ?", new String[]{id});
                    db.close();
                    return true;
                } catch (Exception e) {
                    return false; }

            }//metodo modificar prestamo
            public boolean actualizarIndice(int index) {
                try { //update (tabla,valores,condicion wherer)
                modoEscritura(); String sql = "update " + NAME_TABLE3 + " set indice = " + index + " where " + ID3 + " = 1;";
                    db.execSQL(sql);
                    db.close();
                    return true;
                } catch (Exception e) {
                    return false; }
            }//metodo modificar prestamo

    public Vector getCategorias() { Vector<String> v = new Vector<String>();
        String sql = "select categoria from categorias;"; v.add("-Escoja una categoria-");
        try {
            modoLectura();
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) { v.add(cursor.getString(0));
            } v.add("-Agregar nueva categoria-");
            cursor.close();
            db.close();
            return v;
        } catch (Exception e) {
            return null; }

    }//retornamos vector de categorias

    public Vector getCategoriasEliminar() {
        Vector<String> v = new Vector<String>();
        String sql = "select categoria from categorias;"; v.add("-Escoja una categoria a Eliminar-");
        try { modoLectura();
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                v.add(cursor.getString(0));
            }
            v.add("-Agregar nueva categoria-");
            cursor.close();
            db.close();
            return v;
        } catch (Exception e) {
            return null; } }

            //retornamos vector de categorias
            public Vector getRegistrosFull() { Vector<String> v = new Vector<String>();
                String sql = "select * from registros;";
                try {
                    modoLectura();
                    Cursor cursor = db.rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        v.add("" + cursor.getInt(0) + "," + cursor.getString(1) + "," + cursor.getString(2) + "," + cursor.getString(3) + "," + cursor.getString(4) + "," + cursor.getString(5));
                    } cursor.close();
                    db.close();
                    return v;
                } catch (Exception e) {
                    return null; }
            }//retornamos vector de categorias

    public Vector getDevolucionesFull() { Vector<String> v = new Vector<String>();
        String sql = "select * from " + NAME_TABLE3 + ";";
        try {
            modoLectura();
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) { v.add("" + cursor.getInt(0) + "," + cursor.getString(1) + "," + cursor.getString(2) + "," + cursor.getString(3));
            } cursor.close();
            db.close();
            return v;
        } catch (Exception e) {
            return null; }
    }//retornamos vector de categorias
    public Vector getRegistrosLista() { Vector v = new Vector();
        String sql = "select nombre,articulo from registros;";
        try { modoLectura();
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) { v.add(cursor.getString(0) + "\nLe Preste: " + cursor.getString(1));
            }
            v.add("-Agregar nueva categoria-");
            cursor.close();
            db.close();
            return v; }
        catch (Exception e) {
            return null; }
    }//retornamos vector de categorias

    public Vector getDevolucionesLista() { Vector v = new Vector();
        String sql = "select objeto,nombre,recuperado from " + NAME_TABLE3 + " ;";
        try { modoLectura();
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) { v.add(cursor.getString(1) + "\nRegreso: " + cursor.getString(0) + "\nRecuperado el: " + cursor.getString(2)); }
            v.add("-Agregar nueva categoria-");
            cursor.close();
            db.close();
            return v;
        } catch (Exception e) {
            return null; }
    }//retornamos vector de categorias

    public boolean registrosVacios() {
        String sql = "select nombre,articulo from registros;";
        boolean ans = false; try { modoLectura();
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext() == false) {
                ans = true; }
        } catch (Exception e) { } finally { return ans; }

    }//retornamos vector de categorias

     public int getIndice() { String sql = "select * from " + NAME_TABLE3 + ";"; int i = -1;
         try {
             modoLectura();
             Cursor cursor = db.rawQuery(sql, null);
             cursor.moveToNext(); i = cursor.getInt(1);
             cursor.close();
             db.close();
             return i;
         } catch (Exception e) {
             return i; }
     }//retornar indice
     public boolean devolucionesVacias() {
         String sql = "select objeto,nombre from " + NAME_TABLE3 + ";";
         boolean ans = false;
         try {
             modoLectura();
             Cursor cursor = db.rawQuery(sql, null);
             if (cursor.moveToNext() == false) {
                 ans = true; }
         } catch (SQLiteException e) {
             return ans; } finally { return ans; }
     } /* public void iniciarTabla3(){
      String sql="insert into indices("+NAME_INDICE+")
      values(0);";
      modoEscritura();
       db.execSQL(sql);
       db.close();
       }//la ejecutamos al instalar por primera vez la aplicacion */
}//class