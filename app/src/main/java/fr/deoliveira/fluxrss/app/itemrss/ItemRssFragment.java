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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemRssFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemRssFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemRssFragment extends Fragment implements ItemRssProvider.OnFeedParsed {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_URL = "url";

    // TODO: Rename and change types of parameters

    private int position;
    private String url;
    private ItemRssProvider itemsRssProvider;
    private ArrayAdapter<ItemRss> itemRssAdapter;
    private ListView listViewItemRss;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url the url to parse.
     * @return A new instance of fragment ItemRssFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        //listViewItemRss.setOnItemClickListener(this);
        loadItems();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_rss, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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
       // this.itemRssAdapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadItems() {
//        if ((url == null) || url.trim().equals("")) {
//            return 0;
//        }
        //On init le provider avec l'url seulement la liste items est renvoyée.
        // ItemsRssProviderInterface podcastProviderInterface = new ItemRssProvider(url);
        this.itemsRssProvider = new ItemRssProvider(url, this);
        this.itemsRssProvider.parseUrl();
    }

    public int displayPodcasts() {
        List<ItemRss> podcasts = this.itemsRssProvider.getListItemsRss();
        for (ItemRss itemRss : podcasts) {
            Log.i("testGETFRAGMENT?", itemRss.getTitre());
        }
        Log.i("podcastSize", podcasts.size() + "");
        Bind(podcasts);
        //Intent intent = this.getIntent();
        //intent.putExtra("nbArticle", podcasts.size());
        //setResult(Activity.RESULT_OK, intent);
        return podcasts.size();

    }

    private void Bind(List<ItemRss> itemsRss) {
        if (this.getActivity() != null) {
            this.itemRssAdapter = new ItemRssAdapter(this.getActivity().getBaseContext(), itemsRss);
            Log.i("testGetCountAdapter", itemRssAdapter.getCount() + "");
            this.listViewItemRss.setAdapter(itemRssAdapter);
        }
    }


}
