package fr.deoliveira.fluxrss.app.itemrss;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fd
 * Date: 28/11/13
 * Time: 14:48
 */
public interface ItemsRssProviderInterface {
    String getName();
    String getUrl();
    List<ItemRss> getListItemsRss();
}
