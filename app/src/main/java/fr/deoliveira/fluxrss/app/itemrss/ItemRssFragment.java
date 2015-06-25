package fr.deoliveira.fluxrss.app.itemrss;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.deoliveira.fluxrss.app.R;

import java.util.List;

public class ItemRssFragment extends Fragment implements ItemRssProvider.OnFeedParsed {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_URL = "url";


    private int position;
    private String url;
    private ItemRssProvider itemsRssProvider;
    private ArrayAdapter<ItemRss> itemRssAdapter;
    private ListView listViewItemRss;


    private OnItemRssInteractionListener mListener;

    public static ItemRssFragment newInstance(String url) {
        ItemRssFragment fragment = new ItemRssFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    public ItemRssFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_rss, container, false);
        listViewItemRss = (ListView) rootView.findViewById(R.id.listItem);
        loadItems();
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onItemRssInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnItemRssInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFeedParsed() {
        this.displayPodcasts();
    }

    public interface OnItemRssInteractionListener {
        void onItemRssInteraction(Uri uri);
    }

    private void loadItems() {
        this.itemsRssProvider = new ItemRssProvider(url, this);
        this.itemsRssProvider.parseUrl();
    }

    public int displayPodcasts() {
        List<ItemRss> podcasts = this.itemsRssProvider.getListItemsRss();
        Bind(podcasts);
        return podcasts.size();

    }

    private void Bind(List<ItemRss> itemsRss) {
        if (this.getActivity() != null) {
            this.itemRssAdapter = new ItemRssAdapter(this.getActivity(), itemsRss);
            this.listViewItemRss.setAdapter(itemRssAdapter);
        }
    }


}
