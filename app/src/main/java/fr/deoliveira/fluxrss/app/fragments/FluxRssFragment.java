package fr.deoliveira.fluxrss.app.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;
import com.crazyhitty.chdev.ks.rssmanager.OnRssLoadListener;
import com.crazyhitty.chdev.ks.rssmanager.RssItem;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;
import fr.deoliveira.fluxrss.app.R;
import fr.deoliveira.fluxrss.app.adapters.ItemRssAdapter;
import fr.deoliveira.fluxrss.app.base.RSSContentProvider;
import fr.deoliveira.fluxrss.app.base.RSSTable;
import fr.deoliveira.fluxrss.app.model.FluxRss;
import fr.deoliveira.fluxrss.app.model.ItemRss;
import fr.deoliveira.fluxrss.app.model.TypeInfo;

import java.util.ArrayList;
import java.util.List;

public class FluxRssFragment extends Fragment implements
        AdapterView.OnItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor>,
        OnRssLoadListener {

    private static final String ARG_PARAM_TYPE = "typeInfo";

    private TypeInfo type;
    private String typeStr;

    // private ArrayAdapter<FluxRss> fluxRssAdapter;
    private ListView listViewItemRss;
    private ItemRssAdapter itemRssAdapter;
    private List<FluxRss> listeFlux;

    private OnFluxRssInteractionListener mListener;

    public static FluxRssFragment newInstance(String typeInfoStr) {
        FluxRssFragment fragment = new FluxRssFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TYPE, typeInfoStr);
        fragment.setArguments(args);
        return fragment;
    }

    public FluxRssFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String typeStr = getArguments().getString(ARG_PARAM_TYPE);
            this.type = TypeInfo.valueOf(typeStr);
            this.typeStr = typeStr;
        }
        this.loadDataFromDb();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flux_rss, container, false);
        listViewItemRss = (ListView) rootView.findViewById(R.id.listFlux);
        listViewItemRss.setOnItemClickListener(this);
        return rootView;
    }


    private void loadDataFromDb() {

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = "";
        String[] projection = null;
        selection = RSSTable.COLUMN_TYPE + " = ?";
        String[] selectionArgs = new String[]{this.typeStr};
        Log.i(this.getClass().getName(), "Creation Loader DB RSS Content provider");
        Log.i(this.getClass().getName(), "Type Str" + typeStr);
        CursorLoader cursorLoader = new CursorLoader(this.getActivity(),
                RSSContentProvider.CONTENT_URI, projection, selection, selectionArgs, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<FluxRss> rsses = RSSTable.mapFromCursor(data);
        Log.i(this.getClass().getName(), "CallBack RSS Load Data DB : " + rsses.size());
        this.listeFlux = rsses;
        loadFeeds();
        //loadFeeds2();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i(this.getClass().getName(), "CallBack RSS Load Reset DB : ");
    }

    public void onButtonPressed(String url) {
        if (mListener != null) {
            mListener.onFluxRssInteraction(url);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFluxRssInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RssItem itemRss = (RssItem) listViewItemRss.getItemAtPosition(position);
        String url = itemRss.getLink();
        this.onButtonPressed(url);
    }

    private void loadFeeds() {
        //you can also pass multiple urls
        // String[] urlArr = {"http://feeds.bbci.co.uk/news/rss.xml"};
        String[] urlArr = new String[this.listeFlux.size()];
        List<String> listUrl = new ArrayList<>();
        for (FluxRss flux : this.listeFlux) {
            listUrl.add(flux.getUrl());
        }

        listUrl.toArray(urlArr);
        for (String str : urlArr) {
            Log.d(this.getClass().getName(), "TEST : " + str);
        }
        RssReader rs = new RssReader(this.getContext());
        //rs.onFailure();
        rs.showDialog(false);
        rs.urls(urlArr);
        rs.parse(this);
    }

    private void loadFeeds2() {

        String[] urlArr = new String[this.listeFlux.size()];
        List<String> listUrl = new ArrayList<>();
        for (FluxRss flux : this.listeFlux) {
            listUrl.add(flux.getUrl());
        }

        listUrl.toArray(urlArr);
        for (String str : urlArr) {
            SimpleRss2Parser parser = new SimpleRss2Parser(str, new SimpleRss2ParserCallback() {
                @Override
                public void onFeedParsed(List<RSSItem> items) {
                    bind2(items);
                }

                @Override
                public void onError(Exception ex) {
                    Log.e(this.getClass().getName(), ex.getMessage());
                }
            }
            );
            Log.d(this.getClass().getName(), "TEST : " + str);
        }


    }

    private void bind(List<RssItem> list) {
        List<ItemRss> listeConv = new ArrayList<>();
        for(RssItem item : list){
            ItemRss itemRss =  new ItemRss();
            itemRss.setDescription(item.getDescription());
            itemRss.setLien(item.getLink());
            itemRss.setTitre(item.getTitle());
            itemRss.setLienImage(item.getImageUrl());


            listeConv.add(itemRss);
        }



        this.itemRssAdapter = new ItemRssAdapter(this.getActivity(), listeConv);
        this.listViewItemRss.setAdapter(itemRssAdapter);
    }

    private void bind2(List<RSSItem> list) {

        List<ItemRss> listeConv = new ArrayList<>();
        for(RSSItem item : list){
            ItemRss itemRss =  new ItemRss();
            itemRss.setDescription(item.getDescription());
            itemRss.setLien(item.getLink().toString());
            itemRss.setTitre(item.getTitle());
            listeConv.add(itemRss);
        }

        this.itemRssAdapter = new ItemRssAdapter(this.getActivity(), listeConv);
        this.listViewItemRss.setAdapter(itemRssAdapter);
    }

    @Override
    public void onSuccess(List<RssItem> list) {
        bind(list);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this.getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }


//    @Override
//    public void onFeedParsed(List<RSSItem> list) {
//
//    }
//
//    @Override
//    public void onError(Exception e) {
//
//    }


    public interface OnFluxRssInteractionListener {
        void onFluxRssInteraction(String url);

    }

}
