package fr.deoliveira.fluxrss.app.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fr.deoliveira.fluxrss.app.model.FluxRss;
import fr.deoliveira.fluxrss.app.model.TypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romain on 30/09/2015.
 */
public class RSSTable {

    // Database table
    public static final String TABLE_RSS = "rss";
    public static final String COLUMN_KEY = "_id";
    public static final String COLUMN_AUTEUR = "auteur";
    public static final String COLUMN_LIEN = "url";
    public static final String COLUMN_TYPE = "type";

    // Database creation SQL statement

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_RSS + " (" + COLUMN_KEY
                    + " integer primary key autoincrement,"
                    + COLUMN_AUTEUR + " TEXT, "
                    + COLUMN_LIEN + " TEXT, "
                    + COLUMN_TYPE + " TEXT);";

    private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_RSS + ";";


    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(RSSTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL(TABLE_DROP);
        onCreate(database);
    }

    public static List<FluxRss> mapFromCursor(Cursor cursor) {
        List<FluxRss> listeFlux = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                FluxRss fluxRss = new FluxRss(cursor.getInt(0), cursor.getString(1), cursor.getString(2), TypeInfo.valueOf(cursor.getString(3)));
                listeFlux.add(fluxRss);
            }
        }
        return listeFlux;
    }

    public static List<ContentValues> getContentValuesFromFlux(List<FluxRss> fluxRsses) {
        List<ContentValues> contentValues = new ArrayList<>();
        for (FluxRss fluxRss : fluxRsses) {
            ContentValues values = new ContentValues();
            //values.put(COLUMN_KEY, fluxRss.getId());
            values.put(COLUMN_AUTEUR, fluxRss.getAuteur());
            values.put(COLUMN_LIEN, fluxRss.getUrl());
            values.put(COLUMN_TYPE, fluxRss.getType().name());
            contentValues.add(values);
        }
        return contentValues;
    }

    public static String[] getProjection() {
        return new String[]{"COUNT(*)", COLUMN_KEY,
                COLUMN_AUTEUR,
                COLUMN_LIEN,
                COLUMN_TYPE};
    }

    public static List<FluxRss> getDefaultFlux() {
        List<FluxRss> fluxRsss = new ArrayList<>();
        FluxRss fluxRss;
        //UNE
        fluxRss = new FluxRss("Figaro", "http://rss.lefigaro.fr/lefigaro/laune", TypeInfo.UNE);
        fluxRsss.add(fluxRss);
        fluxRss = new FluxRss("Europe 1", "http://europe1.fr.feedsportal.com/c/32376/f/546038/index.rss", TypeInfo.UNE);
        fluxRsss.add(fluxRss);
        fluxRss = new FluxRss("Le Monde", "http://rss.lemonde.fr/c/205/f/3050/index.rss", TypeInfo.UNE);
        fluxRsss.add(fluxRss);

        //Culture
        fluxRss = new FluxRss("Le Monde Culture","http://rss.lemonde.fr/c/205/f/3060/index.rss",TypeInfo.CULTURE);
        fluxRsss.add(fluxRss);
        fluxRss = new FluxRss("Mr Mondialisation", "https://mrmondialisation.org/feed/", TypeInfo.CULTURE);
        fluxRsss.add(fluxRss);

        //Sport
        fluxRss = new FluxRss("Eurosport", "http://www.eurosport.fr/rss.xml", TypeInfo.SPORT);
        fluxRsss.add(fluxRss);
        fluxRss= new FluxRss("Le monde sport","http://rss.lemonde.fr/c/205/f/3058/index.rss",TypeInfo.SPORT);
        fluxRsss.add(fluxRss);
        fluxRss= new FluxRss("L'équipe","http://www.lequipe.fr/rss/actu_rss.xml",TypeInfo.SPORT);
        fluxRsss.add(fluxRss);


        //Economie
        fluxRss = new FluxRss("Le monde Eco","http://rss.lemonde.fr/c/205/f/3055/index.rss",TypeInfo.ECONOMIE);
        fluxRsss.add(fluxRss);
        fluxRss = new FluxRss("Les Echos","http://syndication.lesechos.fr/rss/rss_france.xml",TypeInfo.ECONOMIE);
        fluxRsss.add(fluxRss);

        //Techno
        fluxRss = new FluxRss("Le monde tech","http://rss.lemonde.fr/c/205/f/3061/index.rss",TypeInfo.TECHNOLOGIES);
        fluxRsss.add(fluxRss);
        fluxRss = new FluxRss("Le figaro tech","http://www.lefigaro.fr/rss/figaro_hightech.xml",TypeInfo.TECHNOLOGIES);
        fluxRsss.add(fluxRss);

        //Sante
        fluxRss = new FluxRss("Le monde santé","http://www.lemonde.fr/sante/rss_full.xml",TypeInfo.SANTE);
        fluxRsss.add(fluxRss);
        fluxRss = new FluxRss("Le figaro santé","http://www.lefigaro.fr/rss/figaro_sante.xml",TypeInfo.SANTE);
        fluxRsss.add(fluxRss);

        //International
        fluxRss =new FluxRss("Le monde international","http://rss.lemonde.fr/c/205/f/3052/index.rss",TypeInfo.INTERNATIONAL);
        fluxRsss.add(fluxRss);
        fluxRss=  new FluxRss("France 24","http://www.france24.com/fr/actualites/rss",TypeInfo.INTERNATIONAL);
        fluxRsss.add(fluxRss);

        return fluxRsss;
    }
}
