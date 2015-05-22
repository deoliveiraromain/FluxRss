package fr.deoliveira.fluxrss.app.itemrss;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.deoliveira.fluxrss.app.R;
import fr.deoliveira.fluxrss.app.fluxrss.FluxRss;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fd
 * Date: 28/11/13
 * Time: 15:15
 */
public class ItemRssAdapter extends ArrayAdapter<ItemRss> {
    private final List<ItemRss> itemsRss;
    private Activity activity;
    private Context context;

//    public ItemRssAdapter(PodcastFeedActivity rssFeedActivity, List<ItemRss> itemsRss) {
//
//
//        // TODO : android.R.layout.simple_list_item_single_choice  => tester pour le choix
//        super(rssFeedActivity, R.layout.itemrss, itemsRss);
//        Log.i("testGetView", "l" + itemsRss.size());
//        this.itemsRss = itemsRss;
//        activity = rssFeedActivity;
//    }

    public ItemRssAdapter(Context context, List<ItemRss> listeItemsRss) {
        super(context, R.layout.itemrss, listeItemsRss);
        Log.i("testGetView", "" +listeItemsRss.size());
        this.itemsRss = listeItemsRss;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View conView;
        ViewHelper viewHelper;

        conView = convertView;
        if (convertView == null) {
            Log.i("testGetView", "testgetView");
            //LayoutInflater layoutInflater = activity.getLayoutInflater();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            conView = layoutInflater.inflate(R.layout.itemrss, null); //true ?
            viewHelper = new ViewHelper();
            viewHelper.lien = (TextView) conView.findViewById(R.id.lien);
            viewHelper.resume = (TextView) conView.findViewById(R.id.resume);
            viewHelper.titre = (TextView) conView.findViewById(R.id.titre);
            conView.setTag(viewHelper);
        } else {
            viewHelper = (ViewHelper) convertView.getTag();
            Log.i("testGetView", "testgetViewFalse");
        }

        ItemRss podcast = itemsRss.get(position);
        Log.i("testGetView", "testgetView"+podcast.titre);
        viewHelper.lien.setText(podcast.getLien());
        viewHelper.resume.setText(podcast.getResume());
        viewHelper.titre.setText(podcast.getTitre());


        return conView;
    }

//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {return  null;}

    class ViewHelper {
        public TextView titre;
        public TextView resume;
        public TextView lien;
    }
}
