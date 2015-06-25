package fr.deoliveira.fluxrss.app.fluxrss;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.deoliveira.fluxrss.app.R;

import java.util.List;

public class FluxRssFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ArrayAdapter<FluxRss> fluxRssAdapter;
    private ListView listViewFluxRss;
    private FluxRssProviderInterface fluxRssProvider;


    private OnFluxRssInteractionListener mListener;

    public static FluxRssFragment newInstance(String param1, String param2) {
        FluxRssFragment fragment = new FluxRssFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FluxRssFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flux_rss, container, false);
        listViewFluxRss = (ListView) rootView.findViewById(R.id.listFlux);
        listViewFluxRss.setOnItemClickListener(this);
        loadFluxRss();
        return rootView;
    }

    public void onButtonPressed(String url) {
        if (mListener != null) {
            mListener.onFluxRssInteraction(url);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFluxRssInteractionListener) activity;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FluxRss source = (FluxRss) listViewFluxRss.getItemAtPosition(position);
        String url = source.getUrl();
        this.onButtonPressed(url);
    }

    public interface OnFluxRssInteractionListener {
        void onFluxRssInteraction(String url);
    }


    private int loadFluxRss() {
        // TODO : ici le provider ira chercher les podcasteurs EN BDD, pour chaque on a une ligne dans l'adapter
        fluxRssProvider = new FluxRssProvider(this.getActivity());
        List<FluxRss> podcasts = fluxRssProvider.getListFluxRss();
        Bind(podcasts);
        return podcasts.size();
    }

    private void Bind(List<FluxRss> listeFlux) {
        this.fluxRssAdapter = new FluxRssAdapter(this.getActivity(), listeFlux);
        this.listViewFluxRss.setAdapter(fluxRssAdapter);
    }

}
