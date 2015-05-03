package fr.deoliveira.fluxrss.app.podcast;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;
import fr.deoliveira.fluxrss.app.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fd
 * Date: 28/11/13
 * Time: 10:57
 */
public class PodcastFeedActivity extends ListActivity {
    private String url;

    private int position;
    private final List<RSSItem>  listeItems =new ArrayList<RSSItem>();
    private static final String toto = "toto";
    private PodCastProvider podCastProvider;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.feedlist_activity);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            url = bundle.getString("URL");
            position = bundle.getInt("position");
        }
        //TODO : faire une classe parser à part qui extends,
        //on va dire que c'est le provider qui doit voir le parser
        //A vérifier, une sorte de modèle en couche,  Ici la vue graphique
        //Elle a la vue widget (attribut listview en protected hérité de ListActivity)
        //Elle a un adapter qui est sété à la vue widget
        //On utilise un provider pour nourir l'adapter
        //Le provider doit voir le parser et que lui doit le voir,
        //on lui file (au provider) l'url pour que son parser puisse parser (que lui doit voir listItem)
        loadPodcast();
    }

    private void loadPodcast() {
//        if ((url == null) || url.trim().equals("")) {
//            return 0;
//        }
        //On init le provider avec l'url seulement la liste items est renvoyée.
       // PodcastProviderInterface podcastProviderInterface = new PodCastProvider(url);
        this.podCastProvider= new PodCastProvider(url,this);
        this.podCastProvider.parseUrl();
    }

    public int displayPodcasts(){
        List<Podcast> podcasts = this.podCastProvider.getPodcast();
        Bind(podcasts);
        Intent intent = this.getIntent();
        intent.putExtra("nbArticle", podcasts.size());
        setResult(Activity.RESULT_OK, intent);
        return podcasts.size();

    }

    private void Bind(List<Podcast> podcasts) {
        ArrayAdapter<Podcast> podcastInfoArrayAdapter = new PodcastAdapter(this, podcasts);
        setListAdapter(podcastInfoArrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Podcast podcast = (Podcast) getListView().getItemAtPosition(position);
        Toast.makeText(getApplicationContext(), podcast.getTitre() + "\n" + podcast.getResume(), Toast.LENGTH_LONG).show();

    }
}