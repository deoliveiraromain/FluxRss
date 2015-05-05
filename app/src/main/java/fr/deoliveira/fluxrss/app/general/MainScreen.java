package fr.deoliveira.fluxrss.app.general;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
//import android.widget.Toolbar;
import fr.deoliveira.fluxrss.app.R;

import java.util.Locale;


/**
 * Created by Romain on 03/02/2015.
 */
public class MainScreen  extends ActionBarActivity{

    private final static int internalRequestCode=559065419;
    private ImageButton buttonAdd;
    private ImageButton buttonView;
    private ImageButton buttonDel;
    private ImageButton buttonOptions;
    //TODO : voir Slidder, puis tester les fragments avec le viewPager, et voir NavigationDrawer

    private DrawerLayout drawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //Uri telephone = Uri.parse("tel:0606060606");
//        String query = "Test recherche";
//        Uri uriQuery = Uri.parse(query);
//        Intent i = new Intent(Intent.ACTION_WEB_SEARCH,uriQuery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);

        //setActionBar(toolbar); si on extends par ActionBarActivity pour la compat

        setSupportActionBar(toolbar);
       // toolbar.setNavigationIcon(android.R.drawable.ic_media_previous);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

       // toolbar.setLogo(R.drawable.ic_launcher);.


       // buttonAdd = (ImageButton) findViewById(R.id.addButton);
       // buttonView = (ImageButton) findViewById(R.id.viewButton);
       // buttonDel = (ImageButton) findViewById(R.id.deleteButton);
       // buttonOptions = (ImageButton) findViewById(R.id.optionsButton);

//        buttonView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              // String test=  getResources().getString(R.string.addPodcasts);
//                Intent intent = new Intent(MainScreen.this, ViewPodcastScreen.class);
//                // intent.putExtra("URL","http://lescastcodeurs.com/feed.atom");
//                startActivity(intent);
//                //startActivityForResult(intent, internalRequestCode);
//            }
//        } ) ;

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles=getResources().getStringArray(R.array.toolbar_arrays);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        //TODO : à customiser
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,/* DrawerLayout object */
                toolbar,
                //R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
       drawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //TODO : là on aura à définir plusieurs fragments pour les vues. donc à faire plusieurs layouts
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.toolbar_arrays)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }

}
