package martis.project.martischaniaupdated.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import martis.project.martischaniaupdated.R;

/**
 * Created by GAK on 4/3/2016.
 */
public class Settings extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        TextView SettingsFragment = (TextView) view.findViewById(R.id.Settings);
        SettingsFragment.setText("Settings Fragment");
        return view;
    }
}
