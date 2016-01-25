package fr.deoliveira.fluxrss.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fr.deoliveira.fluxrss.app.R;
import fr.deoliveira.fluxrss.app.model.ItemRss;

import java.util.List;

public class ItemRssAdapter extends RecyclerView.Adapter<ItemRssAdapter.ViewHolder> {


    private final List<ItemRss> listeItems;
    private Context context;

    @Override
    public ItemRssAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View eventView = inflater.inflate(R.layout.itemrss, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(eventView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemRssAdapter.ViewHolder holder, int position) {
        ItemRss itemRss = this.listeItems.get(position);
        holder.titre.setText(itemRss.getTitre());
        holder.description.setText(Html.fromHtml(itemRss.getDescription()));
        holder.date.setText(itemRss.getDate());
        // Here you apply the animation when the view is bound
        //setAnimation(holder.itemView, position);
    }


    @Override
    public void onViewDetachedFromWindow(ItemRssAdapter.ViewHolder holder) {
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return listeItems.size();
    }

    public ItemRssAdapter(List<ItemRss> listeInvitations) {
        this.listeItems = listeInvitations;
    }

    public void add(List<ItemRss> listeToAdd) {
        this.listeItems.addAll(listeToAdd);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titre;
        private TextView description;
        private TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            titre = (TextView) itemView.findViewById(R.id.titre);
            description = (TextView) itemView.findViewById(R.id.resume);
            date = (TextView) itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            ItemRss item = listeItems.get(position);
            String url = item.getLien();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        }

    }
}
