package fr.deoliveira.fluxrss.app.utils;

import android.util.Log;
import fr.deoliveira.fluxrss.app.model.FluxRss;
import fr.deoliveira.fluxrss.app.model.TypeInfo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Romain on 18/01/2016.
 */
public class RSSFeeder {

    // A set to be sure that there is no duplicated points.
    private Set<FluxRss> listeRSS;
    // The path to the given file to read points from.
    private String filePath;

    public RSSFeeder(String filePath) {
        this.filePath = filePath;
        this.listeRSS = new HashSet<>();
    }

    /**
     * readFromFile reads file given by {@link #filePath}
     *
     * @return <true> if file well read, <false> if error occurs.
     */
    public boolean readFromFile() {
        FileReader fr = null;
        LineNumberReader lr = null;
        try {
            fr = new FileReader(this.filePath);
            lr = new LineNumberReader(fr);
            String ligne = null;
            while ((ligne = lr.readLine()) != null) {
                try {
                    readRSS(ligne, lr.getLineNumber());
                } catch (MalFormedRSSException e) {
                    Log.e(this.getClass().getName(), e.getMessage());
                    e.printStackTrace();
                    return false;
                } catch (NumberFormatException e1) {
                    Log.e(this.getClass().getName(), e1.getMessage());
                    e1.printStackTrace();
                    return false;
                }
            }
            lr.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.e(this.getClass().getName(), "File not found ... " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException e1) {
            Log.e(this.getClass().getName(), "Error closing file ... " + e1.getMessage());
            e1.printStackTrace();
            return false;
        }

    }

    /**
     * readRSS reads a specific line of file and if succeed adds this line to
     * {@link #listeRSS}
     *
     * @param line       the line to read
     * @param lineNumber the number of the read line.
     * @throws MalFormedRSSException if a line is not well formed.
     */
    private void readRSS(String line, int lineNumber) throws MalFormedRSSException {
        String[] splited = line.split(";");
        if (splited.length != 3) {
            throw new MalFormedRSSException("The line " + lineNumber + " Doesn't contains valid RSS");
        }
        String name = splited[0];
        String url = splited[1];
        String type = splited[2];
        if (name == null || name.isEmpty() || url == null || url.isEmpty() ||
                type == null || (TypeInfo.valueOf(type) == null)) {
            throw new MalFormedRSSException("The line " + lineNumber + " Doesn't contains valid RSS");
        }
        this.listeRSS.add(new FluxRss(name, url, type));
    }

    /**
     * getter for {@link #listeRSS}
     *
     * @return {@link #listeRSS}
     */
    public Set<FluxRss> getListeRSS() {
        return listeRSS;
    }


}
