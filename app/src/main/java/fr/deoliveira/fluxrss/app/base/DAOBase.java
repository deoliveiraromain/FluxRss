package fr.deoliveira.fluxrss.app.base;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public abstract class DAOBase {

    protected final static int VERSION = 1;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "FluxRssDatabase.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public static final List<DAOBase> LISTE_TABLES = new ArrayList<>();

    //Ces 3 chaines dépendent de l'instance du DAO fille, donc pas de static
    protected final String nomTable;
    protected final String tableCreate;
    protected final String tableDrop;

    public DAOBase(Context pContext, String nomTable, String tableCreate, String tableDrop) {
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
        this.tableCreate = tableCreate;
        this.nomTable = nomTable;
        this.tableDrop = tableDrop;
        LISTE_TABLES.add(this);
        //Voir si on met ici la vérif de ifEmpty -> populate, car on populate pas forcément toutes les classes
        //Auquel cas on remet le nom table en param et isEmpty en private
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    protected boolean isEmpty() {
        boolean res = false;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*)").append(" FROM ")
                .append(nomTable);
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


    public String getNomTable() {
        return nomTable;
    }

    public String getTableCreate() {
        return tableCreate;
    }

    public String getTableDrop() {
        return tableDrop;
    }
}