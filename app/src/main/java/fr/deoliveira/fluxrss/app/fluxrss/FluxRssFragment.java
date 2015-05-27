package fr.deoliveira.fluxrss.app.fluxrss;

import android.app.Activity;
import android.net.Uri;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FluxRssFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FluxRssFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FluxRssFragment extends Fragment implements AdapterView.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final int internalRequestCode = 568057870;
    private ArrayAdapter<FluxRss> fluxRssAdapter;
    private ListView listViewFluxRss;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FluxRssFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FluxRssFragment newInstance(String param1, String param2) {
        FluxRssFragment fragment = new FluxRssFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FluxRssFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_flux_rss, container, false);
        listViewFluxRss = (ListView) rootView.findViewById(android.R.id.list);
        listViewFluxRss.setOnItemClickListener(this);
        loadFluxRss();
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String url) {
        //TODO : ici qu'on gère un fluxrss cliqué dans la liste des podcasters., on envoie les infos nécessaire au changement de fragment
        if (mListener != null) {
            mListener.onFragmentInteraction(url);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FluxRss source = (FluxRss)listViewFluxRss.getItemAtPosition(position);
        String url =source.getUrl();
        //mListener.onFragmentInteraction(url);
        this.onButtonPressed(url);
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
        void onFragmentInteraction(String url);
    }


    private int loadFluxRss() {
        // TODO : ici le provider ira chercher les podcasteurs EN BDD, pour chaque on a une ligne dans l'adapter
        FluxRssProviderInterface podcastersProviderInterface = new FluxRssProvider();
        List<FluxRss> podcasts = podcastersProviderInterface.getListFluxRss();
        Bind(podcasts);
        return podcasts.size();
    }

    private void Bind(List<FluxRss> listeFlux) {
        this.fluxRssAdapter = new FluxRssAdapter(this.getActivity(), listeFlux);
        this.listViewFluxRss.setAdapter(fluxRssAdapter);
    }

}
