package fr.deoliveira.fluxrss.app.fluxrss;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 09/12/13
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class FluxRss {

    public String auteur;
    public String nbArticles;
    public String url;

    public FluxRss(String auteur, String url) {
        setAuteur(auteur);
        setNbArticles("0 article");
        setUrl(url);


    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getnbPodcasts() {
        return nbArticles;
    }

    public String getUrl() {
        return url;
    }

    public void setNbArticles(String nbArticles) {
        this.nbArticles = nbArticles;
    }

}

