package martis.project.martischaniaupdated.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import martis.project.martischaniaupdated.HttpRequest;
import martis.project.martischaniaupdated.GoogleFormUploader;
import martis.project.martischaniaupdated.R;

/**
 * Created by GAK on 4/3/2016.
 */
public class RateFeedback extends Fragment {
    EditText feed;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate, container, false);
        feed = (EditText) view.findViewById(R.id.feedText);
        feed.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick (View v){
                feed.getText().clear();
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Rate Us!");
        Button button = (Button) view.findViewById(R.id.feedButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = feed.getText().toString();
                Log.i("Error",text);
                GoogleFormUploader uploader = new GoogleFormUploader("1SDQLI4yKvFLY7slJDrnDJhN_heVYstU4NqY9QbmcvwU");
                uploader.addEntry("92935924", text);
                uploader.upload();}
        });
        return view;
    }

}


