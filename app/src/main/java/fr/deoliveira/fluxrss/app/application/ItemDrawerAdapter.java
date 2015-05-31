package fr.deoliveira.fluxrss.app.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.deoliveira.fluxrss.app.R;
import fr.deoliveira.fluxrss.app.fluxrss.FluxRss;

import java.util.List;


/**
 *
 */

public class ItemDrawerAdapter extends ArrayAdapter<ItemDrawer> {

    private final List<ItemDrawer> listeItemsDrawer;
    private Context context;



    public ItemDrawerAdapter(Context context, List<ItemDrawer> listeItemsDrawer) {
        super(context, R.layout.drawer_list_item, listeItemsDrawer);
        this.listeItemsDrawer = listeItemsDrawer;
        this.context = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View conView;
        ViewHelper viewHelper;

        conView = convertView;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            conView = layoutInflater.inflate(R.layout.drawer_list_item, null);
            viewHelper = new ViewHelper();
            viewHelper.titre = (TextView) conView.findViewById(R.id.itemTitre);
            viewHelper.icon = (ImageView) conView.findViewById(R.id.itemIcon);

            conView.setTag(viewHelper);
        } else {
            viewHelper = (ViewHelper) convertView.getTag();
        }

        ItemDrawer item = (ItemDrawer) getItem(position);
      //  FluxRss podcaster = listFluxRss.get(position);
        viewHelper.titre.setText(item.getTitle());
        viewHelper.icon.setImageResource(item.getIcon());


        return conView;
    }

    class ViewHelper {
        public TextView titre;
        public ImageView icon;
    }

}
