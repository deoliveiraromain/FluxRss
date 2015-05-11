package fr.deoliveira.fluxrss.app.fluxrss;

import android.app.Activity;
import android.content.Context;
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
public class FluxRssAdapter extends ArrayAdapter<FluxRss> {

    private final List<FluxRss> listFluxRss;
    private Activity activity;
    private Context context;

    public FluxRssAdapter(ListPodcastersFeedActivity rssFeedActivity, List<FluxRss> listFluxRss) {

        super(rssFeedActivity, R.layout.itemfluxrss, listFluxRss);
        this.listFluxRss = listFluxRss;
        this.activity = rssFeedActivity;

    }

    public FluxRssAdapter(Context context, List<FluxRss> listeFluxRss) {
        super(context, R.layout.itemfluxrss, listeFluxRss);
        this.listFluxRss = listeFluxRss;
        this.context = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View conView;
        ViewHelper viewHelper;

        conView = convertView;
        if (convertView == null) {
            //LayoutInflater layoutInflater = activity.getLayoutInflater();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            conView = layoutInflater.inflate(R.layout.itemfluxrss, null); //true ?
            viewHelper = new ViewHelper();
            viewHelper.auteur = (TextView) conView.findViewById(R.id.auteur);
            viewHelper.nbPodcasts = (TextView) conView.findViewById(R.id.labelArticlesCount);

            conView.setTag(viewHelper);
        } else {
            viewHelper = (ViewHelper) convertView.getTag();
        }

        FluxRss podcaster = (FluxRss) getItem(position);
      //  FluxRss podcaster = listFluxRss.get(position);
        viewHelper.auteur.setText(podcaster.auteur);
        viewHelper.nbPodcasts.setText(podcaster.nbArticles);


        return conView;
    }

    class ViewHelper {
        public TextView auteur;
        public TextView nbPodcasts;
    }

}
