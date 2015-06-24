package fr.deoliveira.fluxrss.app.fluxrss;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 19/12/13
 * Time: 00:00
 * To change this template use File | Settings | File Templates.
 */
public class FluxRssProvider implements FluxRssProviderInterface {

    private String url="";

    private static List<FluxRss> listeFluxRss = initListFluxRss();

    private FluxRssDao fluxRssDao;

    public FluxRssProvider() {
        //this.url=URL;    FluxRssDao  dao = new FluxRssDao(v.getContext());
        this.initListFluxRss();
    }

    private static List<FluxRss> initListFluxRss() {
        if (listeFluxRss ==null) {
            listeFluxRss = new ArrayList<FluxRss>();
            //FIXME
//            FluxRss fluxRss;
//            fluxRss = new FluxRss("Les cast Codeurs", "http://lescastcodeurs.libsyn.com/rss");
//            listeFluxRss.add(fluxRss);
//            fluxRss = new FluxRss("info Q", "http://www.infoq.com/fr/feed?token=E2kbOTr4TsNqNc17hN5zFoxD0rHadgcC");
//            listeFluxRss.add(fluxRss);
//            fluxRss = new FluxRss("Rtl les grosses têtes", "http://www.rtl.fr/podcast/les-grosses-tetes.xml");
//            listeFluxRss.add(fluxRss);
//            fluxRss = new FluxRss("Europe 1", "http://europe1.fr.feedsportal.com/c/32376/f/546038/index.rss");
//            listeFluxRss.add(fluxRss);
//            fluxRss = new FluxRss("Le Monde", "http://rss.lemonde.fr/c/205/f/3050/index.rss");
//            listeFluxRss.add(fluxRss);
        }
        return listeFluxRss;

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
    public List<FluxRss> getListFluxRss() {
        listeFluxRss = fluxRssDao.getAllFlux();
        return listeFluxRss;
    }

    public static int getNbFluxRss(){
        return listeFluxRss.size();
    }


}
