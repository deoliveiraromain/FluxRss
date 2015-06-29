package fr.deoliveira.fluxrss.app.fluxrss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import fr.deoliveira.fluxrss.app.base.DAOBase;

import java.util.ArrayList;
import java.util.List;

public class FluxRssDao extends DAOBase {

    public static final String TABLE_NAME = "fluxrss";


    private static final String KEY = "id";
    private static final String AUTEUR = "auteur";
    private static final String LIEN = "url";
    private static final String TYPE = "type";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AUTEUR + " TEXT, " + LIEN + " TEXT, " + TYPE + " TEXT);";
    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    public FluxRssDao(Context pContext) {
        super(pContext);
        this.open();
        if (this.isEmpty(TABLE_NAME)) {
            this.populateIfEmpty();
        }
        this.close();
    }

    public long ajouter(FluxRss f) {
        this.open();
        long id = 0;
        try {
            ContentValues value = new ContentValues();
            value.put(FluxRssDao.AUTEUR, f.getAuteur());
            value.put(FluxRssDao.LIEN, f.getUrl());
            value.put(FluxRssDao.TYPE, f.getType().toString());
            mDb.beginTransaction();
            id = mDb.insert(FluxRssDao.TABLE_NAME, null, value);
            mDb.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }
        this.close();
        return id;
    }

    public void supprimer(long id) {
        this.open();
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
        this.close();
    }

    public void modifier(FluxRss f) {
        this.open();
        try {
            ContentValues value = new ContentValues();
            value.put(FluxRssDao.AUTEUR, f.getAuteur());
            value.put(FluxRssDao.LIEN, f.getUrl());
            value.put(FluxRssDao.TYPE, f.getType().toString());
            mDb.beginTransaction();
            mDb.update(TABLE_NAME, value, KEY + " = ?", new String[]{String.valueOf(f.getId())});
            mDb.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
        }
        this.close();
    }

    public FluxRss selectionner(long id) {
        this.open();
        FluxRss flux = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(KEY + ",").append(AUTEUR + ",").append(LIEN + ", ").append(TYPE).append(" FROM ")
                .append(TABLE_NAME).append(" WHERE ").append(KEY).append(" = ?");
        String query = sql.toString();
        Cursor c = mDb.rawQuery(query, new String[]{String.valueOf(id)});
        if (c != null) {
            c.moveToFirst();
            flux = new FluxRss(c.getLong(0), c.getString(1), c.getString(2), c.getString(3));
            c.close();
        }
        this.close();
        return flux;
    }

    protected void populateIfEmpty() {
        //TODO : a implémenter
    }


    public FluxRss selectionnerWithAuteur(String auteur) {
        this.open();
        FluxRss flux = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(KEY + ",").append(AUTEUR + ",").append(LIEN + ", ").append(TYPE).append(" FROM ").append(TABLE_NAME).append(" WHERE ").append(AUTEUR).append(" = ?");
        String query = sql.toString();
        Cursor c = mDb.rawQuery(query, new String[]{auteur});
        if (c != null) {
            c.moveToFirst();
            flux = new FluxRss(c.getLong(0), c.getString(1), c.getString(2), c.getString(3));
            c.close();
        }
        this.close();
        return flux;

    }

    public List<FluxRss> getAllFlux() {
        this.open();
        List<FluxRss> listeFlux = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(KEY + ",").append(AUTEUR + ",").append(LIEN + ", ").append(TYPE).append(" FROM ")
                .append(TABLE_NAME);
        String query = sql.toString();
        Cursor c = mDb.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                listeFlux.add(new FluxRss(c.getLong(0), c.getString(1), c.getString(2), c.getString(3)));
            }
            c.close();
        }
        this.close();
        return listeFlux;
    }

}

