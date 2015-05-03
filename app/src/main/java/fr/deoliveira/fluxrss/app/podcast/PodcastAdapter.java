package fr.deoliveira.fluxrss.app.podcast;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.deoliveira.fluxrss.app.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fd
 * Date: 28/11/13
 * Time: 15:15
 */
public class PodcastAdapter extends ArrayAdapter<Podcast> {
    private final List<Podcast> podcasts;
    private Activity activity;

    public PodcastAdapter(PodcastFeedActivity rssFeedActivity, List<Podcast> podcasts) {


        // TODO : android.R.layout.simple_list_item_single_choice  => tester pour le choix
        super(rssFeedActivity, R.layout.item, podcasts);
        this.podcasts = podcasts;
        activity = rssFeedActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View conView;
        ViewHelper viewHelper;

        conView = convertView;
        if (convertView == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            conView = layoutInflater.inflate(R.layout.item, null); //true ?
            viewHelper = new ViewHelper();
            viewHelper.lien = (TextView) conView.findViewById(R.id.lien);
            viewHelper.resume = (TextView) conView.findViewById(R.id.resume);
            viewHelper.titre = (TextView) conView.findViewById(R.id.titre);
            conView.setTag(viewHelper);
        } else {
            viewHelper = (ViewHelper) convertView.getTag();
        }

        Podcast podcast = podcasts.get(position);
        viewHelper.lien.setText(podcast.getLien());
        viewHelper.resume.setText(podcast.getResume());
        viewHelper.titre.setText(podcast.getTitre());

        return conView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {return  null;}

    class ViewHelper {
        public TextView titre;
        public TextView resume;
        public TextView lien;
    }
}
