package fr.deoliveira.fluxrss.app.listpodcasters;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import fr.deoliveira.fluxrss.app.R;
import fr.deoliveira.fluxrss.app.podcast.PodcastFeedActivity;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 19/12/13
 * Time: 00:41
 * To change this template use File | Settings | File Templates.
 */


public class ListPodcastersFeedActivity extends ListActivity {
    private String url;
    private final int internalRequestCode;
    private ArrayAdapter<ListPodcasters> podcastInfoArrayAdapter;

    public ListPodcastersFeedActivity() {
        internalRequestCode = 568057870;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.feedlist_activity);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            //url = bundle.getString("URL");
        }
        intent.putExtra("nbArticle", loadPodcast());
        setResult(Activity.RESULT_OK, intent);


    }

    private int loadPodcast() {
    /*
        if ((url == null) || url.trim().equals("")) {
            return 0;
        }*/
        // TODO : ici le provider ira chercher les podcasteurs EN BDD, pour chaque on a une ligne dans l'adapter
        ListPodcastersProviderInterface podcastersProviderInterface = new ListPodcastersProvider();
        List<ListPodcasters> podcasts = podcastersProviderInterface.getListPodcasters();
        Bind(podcasts);
        return podcasts.size();
    }

    private void Bind(List<ListPodcasters> podcasts) {
        this.podcastInfoArrayAdapter = new ListPodcastersAdapter(this, podcasts);
        setListAdapter(podcastInfoArrayAdapter);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        ListPodcasters podcaster = (ListPodcasters) getListView().getItemAtPosition(position);
        Toast.makeText(getApplicationContext(), podcaster.getAuteur() + "\n" + podcaster.getnbPodcasts(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, PodcastFeedActivity.class);
        intent.putExtra("URL", podcaster.getUrl());
        intent.putExtra("position", position);
        startActivityForResult(intent, internalRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == internalRequestCode) {
            switch (resultCode) {
                case RESULT_OK:
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        int nbArticles = bundle.getInt("nbArticle");
                        int position = bundle.getInt("position");
                        updateLabel(nbArticles, position);
                    }
            }
        }
    }


    private void updateLabel(int nbArticle, int position) {
        if (nbArticle > 0) {
            ListPodcasters podcaster = (ListPodcasters) getListView().getItemAtPosition(position);
            podcaster.setNbPodcasts(String.format(getString(R.string.articleTrouve), nbArticle));
            this.podcastInfoArrayAdapter.notifyDataSetChanged();

        }
    }

}
