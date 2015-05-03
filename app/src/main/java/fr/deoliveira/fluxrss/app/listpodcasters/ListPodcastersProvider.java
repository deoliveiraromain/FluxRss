package fr.deoliveira.fluxrss.app.listpodcasters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 19/12/13
 * Time: 00:00
 * To change this template use File | Settings | File Templates.
 */
public class ListPodcastersProvider implements ListPodcastersProviderInterface {

    private String url="";

    private static List<ListPodcasters> listepodcasters=initListPodcasters();

    public ListPodcastersProvider() {
        //this.url=URL;
        this.initListPodcasters();
    }

    private static List<ListPodcasters> initListPodcasters() {
        if (listepodcasters==null) {
            listepodcasters = new ArrayList<ListPodcasters>();
            ListPodcasters podcaster;
            podcaster = new ListPodcasters("Les cast Codeurs", "http://lescastcodeurs.libsyn.com/rss");
            listepodcasters.add(podcaster);
            podcaster = new ListPodcasters("info Q", "http://www.infoq.com/fr/feed?token=E2kbOTr4TsNqNc17hN5zFoxD0rHadgcC");
            listepodcasters.add(podcaster);
            podcaster = new ListPodcasters("Rtl les grosses têtes", "http://www.rtl.fr/podcast/les-grosses-tetes.xml");
            listepodcasters.add(podcaster);
            podcaster = new ListPodcasters("Europe 1", "http://europe1.fr.feedsportal.com/c/32376/f/546038/index.rss");
            listepodcasters.add(podcaster);
            podcaster = new ListPodcasters("Le Monde", "http://rss.lemonde.fr/c/205/f/3050/index.rss");
            listepodcasters.add(podcaster);
        }
        return listepodcasters;

    }

    @Override
    public String getName() {
        return "To appear";
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public List<ListPodcasters> getListPodcasters() {
      //  List<ListPodcasters> listepodcasters = new ArrayList<ListPodcasters>();
      //  ListPodcasters podcaster;

        return listepodcasters;
    }

    public static int getNbPodcasters(){
        return listepodcasters.size();
    }


}
