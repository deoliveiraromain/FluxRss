package fr.deoliveira.fluxrss.app.base;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {

    protected final static int VERSION = 1;
    // Le nom du fichier qui repr�sente ma base
    protected final static String NOM = "FluxRssDatabase.db";
    protected final static String NOMnoDB = "FluxRssDatabase";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
        //Voir si on met ici la v�rif de ifEmpty -> populate, car on populate pas forc�ment toutes les classes
        //Auquel cas on remet le nom table en param
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la derni�re base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    protected boolean isEmpty(String tableName) {
        boolean res = false;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*)").append(" FROM ")
                .append(tableName);
        String query = sql.toString();
        Cursor c = mDb.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            long id = c.getInt(0);
            if (id == 0) {
                res = true;
            }
        }
        return res;
    }

    protected abstract void populateIfEmpty();

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}