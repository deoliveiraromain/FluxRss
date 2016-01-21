package fr.deoliveira.fluxrss.app.model;

/**
 * Created by Romain on 21/01/2016.
 */
public class ItemRss {

    private String titre;
    private String description;
    private String lien;
    private String lienImage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemRss itemRss = (ItemRss) o;

        if (titre != null ? !titre.equals(itemRss.titre) : itemRss.titre != null) return false;
        if (description != null ? !description.equals(itemRss.description) : itemRss.description != null) return false;
        if (lien != null ? !lien.equals(itemRss.lien) : itemRss.lien != null) return false;
        return lienImage != null ? lienImage.equals(itemRss.lienImage) : itemRss.lienImage == null;

    }

    @Override
    public int hashCode() {
        int result = titre != null ? titre.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (lien != null ? lien.hashCode() : 0);
        result = 31 * result + (lienImage != null ? lienImage.hashCode() : 0);
        return result;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getLienImage() {
        return lienImage;
    }

    public void setLienImage(String lienImage) {
        this.lienImage = lienImage;
    }
}
