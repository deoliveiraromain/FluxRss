package fr.deoliveira.fluxrss.app.itemrss;

import android.util.Log;
import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;

import java.util.ArrayList;
import java.util.List;

public class ItemRssProvider implements ItemsRssProviderInterface {

    private String URL;
    private List<ItemRss> itemsRss = new ArrayList<>();
    private final SimpleRss2Parser parser;

    private OnFeedParsed mListener;

    public ItemRssProvider(String url, final OnFeedParsed itemRssFragment) {
        this.URL = url;
        this.mListener = itemRssFragment;
        this.parser = new SimpleRss2Parser(url,
                new SimpleRss2ParserCallback() {
                    @Override
                    public void onFeedParsed(List<RSSItem> items) {
                        populatePodcastList(items);
                        if (mListener != null) {
                            mListener.onFeedParsed();
                        }
                    }
                    @Override
                    public void onError(Exception ex) {
                        Log.e("testInternet", ex.getMessage());
                    }
                }
        );
    }


    public interface OnFeedParsed {
        public void onFeedParsed();
    }

    public void parseUrl() {
        this.parser.parseAsync();
    }

    private void populatePodcastList(List<RSSItem> items) {
        //TODO : faire une méthode qui vérifie si le podcast est déjà dans la liste
        //TODO : cacher le lien dans un bouton et rajouter description (voir adapter)
        for (RSSItem item : items) {
            ItemRss podcast = new ItemRss(item.getTitle(),item.getDescription(),item.getLink().toString(),item.getDate());
            itemsRss.add(podcast);
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
//        //TODO : faire une méthode qui vérifie si le podcast est déjà dans la liste
        return itemsRss;
    }
}
