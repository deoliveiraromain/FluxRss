package fr.deoliveira.fluxrss.app.listpodcasters;



/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 09/12/13
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class ListPodcasters  {

        public String auteur;
        public String nbPodcasts;
        public String url;

        public ListPodcasters(String auteur, String url) {
            setAuteur(auteur);
            setNbPodcasts("0 article");
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
            return nbPodcasts;
        }
    public String getUrl() {
        return url;
    }

        public void setNbPodcasts(String nbPodcasts) {
            this.nbPodcasts=nbPodcasts;
        }

    }

