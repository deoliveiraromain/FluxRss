package fr.deoliveira.fluxrss.app.fluxrss;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 19/12/13
 * Time: 00:01
 * To change this template use File | Settings | File Templates.
 */
public interface FluxRssProviderInterface {
    String getName();
    String getUrl();
    List<FluxRss> getListFluxRss();
}
