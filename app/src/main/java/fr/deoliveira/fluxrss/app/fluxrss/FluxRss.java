package fr.deoliveira.fluxrss.app.fluxrss;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 09/12/13
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class FluxRss {

    private long id;
    private String auteur;
    private TypeInfo type;
    private String url;

    public FluxRss(long id, String auteur, String url,TypeInfo type) {
        this.id=id;
        this.auteur=auteur;
        this.url=url;
        this.type=type;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }
    public TypeInfo getType() {
        return this.type;
    }

    public void setType(TypeInfo type) {
        this.type = type;
    }

}

