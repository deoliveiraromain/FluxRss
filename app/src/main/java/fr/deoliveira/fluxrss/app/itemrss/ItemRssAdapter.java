package fr.deoliveira.fluxrss.app.itemrss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.deoliveira.fluxrss.app.R;

import java.util.List;

public class ItemRssAdapter extends ArrayAdapter<ItemRss> {
    private final List<ItemRss> itemsRss;
    private Context context;

    public ItemRssAdapter(Context context, List<ItemRss> listeItemsRss) {
        super(context, R.layout.itemrss, listeItemsRss);
        this.itemsRss = listeItemsRss;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View conView;
        ViewHelper viewHelper;

        conView = convertView;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            conView = layoutInflater.inflate(R.layout.itemrss, null); //true ?
            viewHelper = new ViewHelper();
            viewHelper.lien = (TextView) conView.findViewById(R.id.lien);
            viewHelper.resume = (TextView) conView.findViewById(R.id.resume);
            viewHelper.titre = (TextView) conView.findViewById(R.id.titre);
            conView.setTag(viewHelper);
        } else {
            viewHelper = (ViewHelper) convertView.getTag();
        }

        ItemRss podcast = itemsRss.get(position);
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
