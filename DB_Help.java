package www.ittepic.edu.mx.prestapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abril on 27/05/16.
 */
public class DB_Help extends SQLiteOpenHelper {
    private static final String NAME_BD = "Prestar.sqlite";

    public DB_Help(Context contexto){
        super(contexto, NAME_BD, null, 1);
    }
     @Override
    public void onCreate(SQLiteDatabase db){
         db.execSQL(DBManager.CREATE_TABLE);
         db.execSQL(DBManager.CREATE_TABLE2);
         db.execSQL(DBManager.CREATE_TABLE3);
     }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
