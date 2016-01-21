package fr.deoliveira.fluxrss.app.utils;


/**
 * MalFormedRSSException is a custom Exception thrown during reading a file
 * which contains RSS.
 *
 * @author De Oliveira Romain
 * @see Exception
 * @see RSSFeeder
 */
public class MalFormedRSSException extends Exception {

    private static final long serialVersionUID = 506285743284741596L;

    public MalFormedRSSException(String message) {
        super(message);
    }

}
