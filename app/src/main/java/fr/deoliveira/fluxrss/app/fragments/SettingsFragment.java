package fr.deoliveira.fluxrss.app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import fr.deoliveira.fluxrss.app.R;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String TAG_PARAMETERS = "tag_parameters";
    public static final String PREF_KEY_SYNC = "pref_sync";

    private OnParametersFragmentInteractionListener mListener;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.user_settings);
        PreferenceManager.getDefaultSharedPreferences(this.getContext()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnParametersFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnParametersFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i(this.getClass().getName(), "change Preference key :" + key);
        mListener.onParametersFragmentInteraction();
    }

    public interface OnParametersFragmentInteractionListener {
        public void onParametersFragmentInteraction();
    }
}
