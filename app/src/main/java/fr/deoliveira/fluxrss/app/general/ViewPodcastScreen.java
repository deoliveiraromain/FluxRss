package fr.deoliveira.fluxrss.app.general;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import fr.deoliveira.fluxrss.app.R;
import fr.deoliveira.fluxrss.app.listpodcasters.ListPodcastersFeedActivity;
import fr.deoliveira.fluxrss.app.listpodcasters.ListPodcastersProvider;


public class ViewPodcastScreen extends Activity {

    private final static int internalRequestCode=559059070;
    private TextView label;
    private Button buttonRecup;

    public ViewPodcastScreen() {
        super();
        //internalRequestCode = 559059070;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.viewpodcast_activity);
        label = (TextView) findViewById(R.id.labelArticlesCountPodcasters);
        int nbArticle = ListPodcastersProvider.getNbPodcasters();
        label.setText(String.format(getString(R.string.auteursTrouves), nbArticle));

        buttonRecup = (Button) findViewById(R.id.buttonConnect);
        buttonRecup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("testInternet", "Val : " + isOnline());
                getRSS();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == internalRequestCode) {
            switch (resultCode) {
                case RESULT_OK:
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        updateLabel(bundle.getInt("nbArticle"));
                    }
            }
        }
    }

    private void updateLabel(int nbArticle) {
        if (nbArticle > 0) {
            label.setText(String.format(getString(R.string.auteursTrouves), nbArticle));
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void getRSS() {
        Intent intent = new Intent(this, ListPodcastersFeedActivity.class);
        // intent.putExtra("URL","http://lescastcodeurs.com/feed.atom");
        startActivityForResult(intent, internalRequestCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        //R.menu.menu est l'id de notre menu
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

}
