package fr.deoliveira.fluxrss.app.listpodcasters;

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
 * User: Administrator
 * Date: 19/12/13
 * Time: 00:49
 * To change this template use File | Settings | File Templates.
 */
public class ListPodcastersAdapter extends ArrayAdapter<ListPodcasters> {

    private final List<ListPodcasters> listpodcasters;
    private Activity activity;

    public ListPodcastersAdapter(ListPodcastersFeedActivity rssFeedActivity, List<ListPodcasters> listpodcasters) {

        super(rssFeedActivity, R.layout.itempodcasters, listpodcasters);
        this.listpodcasters = listpodcasters;
        this.activity = rssFeedActivity;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View conView;
        ViewHelper viewHelper;

        conView = convertView;
        if (convertView == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            conView = layoutInflater.inflate(R.layout.itempodcasters, null); //true ?
            viewHelper = new ViewHelper();
            viewHelper.auteur = (TextView) conView.findViewById(R.id.auteur);
            viewHelper.nbPodcasts = (TextView) conView.findViewById(R.id.labelArticlesCount);

            conView.setTag(viewHelper);
        } else {
            viewHelper = (ViewHelper) convertView.getTag();
        }

        ListPodcasters podcaster = (ListPodcasters) getItem(position);
      //  ListPodcasters podcaster = listpodcasters.get(position);
        viewHelper.auteur.setText(podcaster.auteur);
        viewHelper.nbPodcasts.setText(podcaster.nbPodcasts);


        return conView;
    }

    class ViewHelper {
        public TextView auteur;
        public TextView nbPodcasts;
    }

}
