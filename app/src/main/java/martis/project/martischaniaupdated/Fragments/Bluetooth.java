package martis.project.martischaniaupdated.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import martis.project.martischaniaupdated.R;

/**
 * Created by GAK on 4/3/2016.
 */
public class Bluetooth  extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bt,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Bluetooth Pairing");

        TextView btFragment = (TextView) view.findViewById(R.id.bt);
        btFragment.setText("Bluetooth Fragment");





        return view;
    }
}
