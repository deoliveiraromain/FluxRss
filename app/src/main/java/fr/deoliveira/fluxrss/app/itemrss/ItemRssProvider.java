package fr.deoliveira.fluxrss.app.itemrss;

import android.util.Log;
import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fd
 * Date: 28/11/13
 * Time: 14:48
 */
public class ItemRssProvider implements ItemsRssProviderInterface {
    private String URL;
    private final List<RSSItem> listeitems;
    private List<ItemRss> itemsRss;
    private final SimpleRss2Parser parser;
    private PodcastFeedActivity feedActivity;

    public ItemRssProvider(String url, final PodcastFeedActivity feedActivity) {
        this.URL = url;
        this.feedActivity=feedActivity;
        this.listeitems = new ArrayList<>();
        //this.listeitems=listeitems;
        this.parser = new SimpleRss2Parser(url,
                new SimpleRss2ParserCallback() {
                    @Override
                    public void onFeedParsed(List<RSSItem> items) {
                        for (int i = 0; i < items.size(); i++) {
                            Log.e("testInternet", items.get(i).getTitle());
                            listeitems.add(items.get(i));
                        }
                        populatePodcastList(items);
                        feedActivity.displayPodcasts();
                        //Intent intent = getIntent();
                        //Bundle bundle = intent.getExtras();

                        //if (bundle != null) {
                        //   url = bundle.getString("URL");
                        //   position = bundle.getInt("position");
                        //}

                        //intent.putExtra("nbArticle", loadPodcast());
                        //setResult(Activity.RESULT_OK, intent);
                    }

                    @Override
                    public void onError(Exception ex) {
                        Log.e("testInternet", ex.getMessage());
                    }
                }
        );
    }

    public void parseUrl() {
        this.parser.parseAsync();
    }

    private void populatePodcastList(List<RSSItem> items){
        itemsRss = new ArrayList<ItemRss>();
        //TODO : faire une m�thode qui v�rifie si le podcast est d�j� dans la liste
        //TODO : cacher le lien dans un bouton et rajouter description (voir adapter)
        for (RSSItem item : items) {
            ItemRss podcast = new ItemRss(item.getTitle(), item.getDate(), item.getLink().toString());
            itemsRss.add(podcast);
            Log.i("testPOdcast?",podcast.getTitre());
        }
    }

    @Override
    public String getName() {
        return "To appear";
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public List<ItemRss> getListItemsRss() {
        Log.i("testgetPodcats","Passage M�thode");
//        List<ItemRss> itemsRss = new ArrayList<ItemRss>();
//        //TODO : faire une m�thode qui v�rifie si le podcast est d�j� dans la liste
//        for (RSSItem itemrss : listeitems) {
//            ItemRss podcast = new ItemRss(itemrss.getTitle(), itemrss.getDate(), itemrss.getLink().toString());
//            itemsRss.add(podcast);
//            Log.i("testPOdcast?",podcast.getTitre());
//        }
//        Log.i("testTailleListe","taille Liste ItemRss "+listeitems.size());
//        Log.i("testTailleListe", "taille Liste ItemRss "+itemsRss.size());
        return itemsRss;

    }


}