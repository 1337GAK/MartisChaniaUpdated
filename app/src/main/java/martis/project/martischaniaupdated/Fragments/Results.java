package martis.project.martischaniaupdated.Fragments;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import martis.project.martischaniaupdated.R;

/**
 * Created by GAK on 4/3/2016.
 */
public class Results extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Project M.A.R.T.I.S.");

        TextView ResultsFragment = (TextView) view.findViewById(R.id.Results);
        ResultsFragment.setText("Results Fragment");
        ImageView heart = (ImageView) view.findViewById(R.id.heartImage);
        TextView bpm = (TextView) view.findViewById(R.id.bpm);
        ImageView bodyTemp = (ImageView) view.findViewById(R.id.bodyTempImage);
        TextView bodyTempText = (TextView) view.findViewById(R.id.bodyTemp);
        ImageView outTemp = (ImageView) view.findViewById(R.id.outTempImage);
        TextView outTempText = (TextView) view.findViewById(R.id.outTemp);
        ImageView dehydration = (ImageView) view.findViewById(R.id.dehydrationImage);
        TextView dehydrationText = (TextView) view.findViewById(R.id.dehydration);




        return view;
    }
}
