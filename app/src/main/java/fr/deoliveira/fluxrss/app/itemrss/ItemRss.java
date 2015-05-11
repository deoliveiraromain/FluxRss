package fr.deoliveira.fluxrss.app.itemrss;

/**
 * Created with IntelliJ IDEA.
 * User: fd
 * Date: 28/11/13
 * Time: 14:49
 */
public class ItemRss {
    public String titre;
    public String resume;
    public String lien;


    public ItemRss() {
    }

    public ItemRss(String titre, String resume, String lien) {
        setTitre(titre);
        setResume(resume);
        setLien(lien);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

}
