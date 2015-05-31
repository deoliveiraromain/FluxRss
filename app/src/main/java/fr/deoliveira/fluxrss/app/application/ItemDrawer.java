package fr.deoliveira.fluxrss.app.application;

/**
 * Created by Romain on 30/05/2015.
 */
public class ItemDrawer {
    private String mTitle;
    private int mIcon;

    public ItemDrawer() {
    }

    public ItemDrawer(String title, int icon) {
        this.mTitle = title;
        this.mIcon = icon;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public int getIcon() {
        return this.mIcon;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setIcon(int icon) {
        this.mIcon = icon;
    }
}
