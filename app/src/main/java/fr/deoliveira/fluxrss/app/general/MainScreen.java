package fr.deoliveira.fluxrss.app.general;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
//import android.widget.Toolbar;
import fr.deoliveira.fluxrss.app.R;


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
        toolbar.setNavigationIcon(android.R.drawable.ic_media_previous);
       // toolbar.setLogo(R.drawable.ic_launcher);.


        buttonAdd = (ImageButton) findViewById(R.id.addButton);
        buttonView = (ImageButton) findViewById(R.id.viewButton);
        buttonDel = (ImageButton) findViewById(R.id.deleteButton);
        buttonOptions = (ImageButton) findViewById(R.id.optionsButton);

        // buttonAdd.setImageResource(R.drawable.);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // String test=  getResources().getString(R.string.addPodcasts);
                Intent intent = new Intent(MainScreen.this, ViewPodcastScreen.class);
                // intent.putExtra("URL","http://lescastcodeurs.com/feed.atom");
                startActivity(intent);
                //startActivityForResult(intent, internalRequestCode);

            }
        } ) ;

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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
