package fr.deoliveira.fluxrss.app.itemrss;

public class ItemRss {
    public String titre;
    public String resume;
    public String lien;
    public String date;


    public ItemRss() {

    }

    public ItemRss(String titre, String resume, String lien,String date) {
        setTitre(titre);
        setResume(resume);
        setLien(lien);
        setDate(date);
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
    public String getDate()
     {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
