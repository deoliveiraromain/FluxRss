package fr.deoliveira.fluxrss.app.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {

    protected final static int VERSION = 1;
    // Le nom du fichier qui représente ma base
    //FluxRssDatabase
    protected final static String NOM = "FluxRssDatabase.db";
    protected final static String NOMnoDB = "FluxRssDatabase";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge

        //mDb = SQLiteDatabase.openDatabase("data/data/fr.deoliveira.fluxrss.app/"+NOMnoDB,
        //        null,
        //        SQLiteDatabase.CREATE_IF_NECESSARY) ;

        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    protected abstract void populateIfEmpty();

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }

    public void test() {
    }
}