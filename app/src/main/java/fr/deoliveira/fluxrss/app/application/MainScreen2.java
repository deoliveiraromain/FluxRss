package fr.deoliveira.fluxrss.app.application;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.ListView;
import fr.deoliveira.fluxrss.app.R;
import fr.deoliveira.fluxrss.app.fluxrss.FluxRssFragment;
import fr.deoliveira.fluxrss.app.itemrss.ItemRssFragment;

/**
 * Created by Romain on 07/05/2015.
 */
public class MainScreen2 extends AppCompatActivity implements FluxRssFragment.OnFragmentInteractionListener, ItemRssFragment.OnFragmentInteractionListener {

    private FragmentNavigationDrawer dlDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.main_activity_2);
        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
           window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(getResources().getColor(R.color.rssRedDark));
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow();
//            w.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            w.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        // Find our drawer view
        dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
        LinearLayout rootDrawerView = (LinearLayout) findViewById(R.id.rootDrawer);
        // Setup drawer view
        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), toolbar,
                R.layout.drawer_list_item, R.id.flContent, rootDrawerView);
        // Add nav items
        dlDrawer.addNavItem("Mes Flux Rss", "First Fragment", FluxRssFragment.class);
        //dlDrawer.addNavItem("");
        //dlDrawer.addNavItem("Second", "Second Fragment", SecondFragment.class);
        //dlDrawer.addNavItem("Third", "Third Fragment", ThirdFragment.class);
        // Select default
        if (savedInstanceState == null) {
            dlDrawer.selectDrawerItem(0);
        }
    }

    @Override
    public void onFragmentInteraction(String url) {
        ItemRssFragment itemRssFragment = null;
        Log.d("testItemRssCreation", "CreationEnCours");
        try {
            itemRssFragment = ItemRssFragment.newInstance(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent, itemRssFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        // and add the transaction to the back stack so the user can navigate back
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content
        if (dlDrawer.isDrawerOpen()) {
            // Uncomment to hide menu items
            // menu.findItem(R.id.mi_test).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (dlDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dlDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }

}