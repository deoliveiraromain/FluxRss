package fr.deoliveira.fluxrss.app.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.crazyhitty.chdev.ks.rssmanager.RssItem;
import com.squareup.picasso.Picasso;
import fr.deoliveira.fluxrss.app.R;
import fr.deoliveira.fluxrss.app.model.ItemRss;
import fr.deoliveira.fluxrss.app.utils.RessourceImageGetter;

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
            viewHelper.description = (TextView) conView.findViewById(R.id.resume);
            viewHelper.titre = (TextView) conView.findViewById(R.id.titre);
            viewHelper.image = (ImageView) conView.findViewById(R.id.image);
            conView.setTag(viewHelper);
        } else {
            viewHelper = (ViewHelper) convertView.getTag();
        }

        ItemRss podcast = itemsRss.get(position);
        viewHelper.lien.setText(podcast.getLien());
       viewHelper.description.setText(Html.fromHtml(podcast.getDescription(), new RessourceImageGetter(context), null));

       // viewHelper.description.setText(Html.fromHtml(podcast.getDescription()));

        viewHelper.titre.setText(podcast.getTitre());
        Picasso.with(context)
                .load(podcast.getLienImage())
                .into(viewHelper.image);
        return conView;
    }

    private static class ViewHelper {
        private TextView titre;
        private TextView description;
        private TextView lien;
        private ImageView image;
    }
}
