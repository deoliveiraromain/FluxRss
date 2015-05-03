package fr.deoliveira.fluxrss.app.podcast;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fd
 * Date: 28/11/13
 * Time: 14:48
 */
public interface PodcastProviderInterface {
    String getName();
    String getUrl();
    List<Podcast> getPodcast();
}
