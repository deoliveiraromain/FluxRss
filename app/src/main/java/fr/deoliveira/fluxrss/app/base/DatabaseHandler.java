package fr.deoliveira.fluxrss.app.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import fr.deoliveira.fluxrss.app.fluxrss.FluxRssDao;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (DAOBase dao : DAOBase.LISTE_TABLES) {
            db.execSQL(dao.getTableCreate());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (DAOBase dao : DAOBase.LISTE_TABLES) {
            db.execSQL(dao.getTableDrop());
        }
        onCreate(db);


    }


}