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
public class RateFeedback extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Rate Us!");
        TextView RateFragment = (TextView) view.findViewById(R.id.Rate);
        RateFragment.setText("Rate Fragment");
        return view;
    }
}
