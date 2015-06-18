package fr.deoliveira.fluxrss.app.fluxrss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.deoliveira.fluxrss.app.base.DAOBase;

public class FluxRssDao extends DAOBase {

    public static final String TABLE_NAME = "fluxrss";
    public static final String KEY = "id";
    public static final String AUTEUR = "auteur";
    public static final String LIEN = "url";
    public static final String TYPE = "type";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AUTEUR + " TEXT, " + LIEN + " TEXT, " + TYPE + " TEXT);";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public FluxRssDao(Context pContext) {
        super(pContext);
    }

    public void ajouter(FluxRss f) {
        ContentValues value = new ContentValues();
        value.put(FluxRssDao.AUTEUR, f.getAuteur());
        value.put(FluxRssDao.LIEN, f.getUrl());
        value.put(FluxRssDao.TYPE,f.getType().toString());
        mDb.insert(FluxRssDao.TABLE_NAME, null, value);
    }

    public void supprimer(long id) {
        mDb.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    public void modifier(FluxRss f) {
        ContentValues value = new ContentValues();
        value.put(FluxRssDao.AUTEUR, f.getAuteur());
        value.put(FluxRssDao.LIEN, f.getUrl());
        value.put(FluxRssDao.TYPE,f.getType().toString());
        mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(f.getId())});
    }

    public FluxRss selectionner(long id) {
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT ").append(AUTEUR+",").append(LIEN+", ").append(TYPE).append(" FROM ").append(TABLE_NAME).append(" WHERE ").append(KEY).append(" = ?");
        String query= sql.toString();
        Cursor c =mDb.rawQuery(query, new String[]{String.valueOf(id)});
        return null;
    }
}

