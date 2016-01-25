package fr.deoliveira.fluxrss.app.application;

import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import fr.deoliveira.fluxrss.app.R;
import fr.deoliveira.fluxrss.app.base.RSSContentProvider;
import fr.deoliveira.fluxrss.app.base.RSSTable;
import fr.deoliveira.fluxrss.app.fragments.AjoutFluxFragment;
import fr.deoliveira.fluxrss.app.fragments.FluxRssFragment;
import fr.deoliveira.fluxrss.app.fragments.SettingsFragment;
import fr.deoliveira.fluxrss.app.model.FluxRss;
import fr.deoliveira.fluxrss.app.model.TypeInfo;
import fr.deoliveira.fluxrss.app.utils.FluxUtils;

import java.util.List;


/**
 * Created by Romain on 07/05/2015.
 */
public class MainScreen extends AppCompatActivity implements
        SettingsFragment.OnParametersFragmentInteractionListener,
        FluxRssFragment.OnFluxRssInteractionListener,
        AjoutFluxFragment.OnAjoutFluxInteractionListener {

    private DrawerLayout dlDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    //TODO: BROADCAST RECEIVER quand on est sur un fragment de ItemRss pour parser r�guli�rement et afficher une notif quand nouveaux items
    //TODO: BROADCAST RECEIVER quand on perd la NETWORK CO
    //TODO : Mettre les requetes dans un HANDLER OU ASYNC (mettre ca dans le Provider ?)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, dlDrawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        dlDrawer.setDrawerListener(mDrawerToggle);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        if (FluxUtils.isFirstLaunch(MainScreen.this)) {
            Log.i(this.getClass().getName(), "FIRST APP LAUNCH !");
            insertDefaultSources();
            FluxUtils.setNotFirstLaunch(MainScreen.this);
        }

        selectDrawerItem(nvDrawer.getMenu().findItem(R.id.nav_top));
    }


    private boolean checkSources() {
        //PROJECTION SELECTION SELECTION ARGS SORT ORDER
        Log.i(this.getClass().getName(), "CHECK RSS SOURCES IN DB...");
        Cursor c = getContentResolver().query(RSSContentProvider.CONTENT_URI, new String[]{"COUNT(*)"}, null, null, null);
        boolean res = c.moveToFirst();
        int nb = 0;
        if (res) {
            nb = c.getInt(0);
            Log.d(this.getClass().getName(), "Nombre de flux : " + nb);
        }
        return res && nb > 0;
    }

    private void insertDefaultSources() {
        Log.i(this.getClass().getName(), "RSS SOURCES EMPTY ...SET TO DEFAULT SOURCES...");
        List<FluxRss> listeFlux = RSSTable.getDefaultFlux();
        List<ContentValues> listeCv = RSSTable.getContentValuesFromFlux(listeFlux);
        for (FluxRss flux : listeFlux) {
            Log.d(this.getClass().getName(), "TEST RSS " + flux.toString());
        }
        for (ContentValues cv : listeCv) {
            Log.d(this.getClass().getName(), "TEST RSS CV2 : " + cv.toString());
            getContentResolver().insert(RSSContentProvider.CONTENT_URI, cv);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        TypeInfo typeInfo = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_top:
                fragmentClass = FluxRssFragment.class;
                typeInfo = TypeInfo.UNE;
                break;
            case R.id.nav_eco:
                fragmentClass = FluxRssFragment.class;
                typeInfo = TypeInfo.ECONOMIE;
                break;
            case R.id.nav_sport:
                fragmentClass = FluxRssFragment.class;
                typeInfo = TypeInfo.SPORT;
                break;
            case R.id.nav_inter:
                fragmentClass = FluxRssFragment.class;
                typeInfo = TypeInfo.INTERNATIONAL;
                break;
            case R.id.nav_tech:
                fragmentClass = FluxRssFragment.class;
                typeInfo = TypeInfo.TECHNOLOGIES;
                break;
            case R.id.nav_cult:
                fragmentClass = FluxRssFragment.class;
                typeInfo = TypeInfo.CULTURE;
                break;
            case R.id.nav_health:
                fragmentClass = FluxRssFragment.class;
                typeInfo = TypeInfo.SANTE;
                break;
            case R.id.nav_ajout:
                fragmentClass = AjoutFluxFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            default:
                fragmentClass = FluxRssFragment.class;
                typeInfo = TypeInfo.UNE;
        }
        try {
            if (fragmentClass.equals(FluxRssFragment.class)) {
                fragment = FluxRssFragment.newInstance(typeInfo.name());
            } else {
                fragment = (Fragment) fragmentClass.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        dlDrawer.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void onAjoutFluxInteraction(Uri uri) {

    }

    @Override
    public void onFluxRssInteraction(String url) {
        Log.d(this.getClass().getName(), "FLUXRSSINTERATION");
//        try {
//            itemRssFragment = ItemRssFragment.newInstance(url);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // Insert the fragment by replacing any existing fragment
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.flContent, itemRssFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }

    @Override
    public void onParametersFragmentInteraction() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(this.getClass().getName(), "ONPAUSE MainActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(this.getClass().getName(), "ONSTOP MainActivity");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this.getClass().getName(), "ONRESUME MainActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(this.getClass().getName(), "ONSTART MainActivity");
    }
}