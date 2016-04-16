package martis.project.martischaniaupdated.Fragments;

import android.app.Fragment;
import android.graphics.Typeface;
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
import android.widget.Toast;

import martis.project.martischaniaupdated.HttpRequest;
import martis.project.martischaniaupdated.GoogleFormUploader;
import martis.project.martischaniaupdated.R;

/**
 * Created by GAK on 4/3/2016.
 */
public class RateFeedback extends Fragment {
    EditText feed;
    TextView text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate, container, false);
        text = (TextView) view.findViewById(R.id.textView12);
        text.setTypeface(Typeface.DEFAULT_BOLD);
        text.setTextSize(15);
        text.setText("Tell us your opinion after experiencing M.A.R.T.I.S., note the features that excited you and the ones that gave you a hard time, and let us know. " +
                "We'll adapt to your needs in no time!");
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
                uploader.upload();
            }
        });

        return view;
    }

}


