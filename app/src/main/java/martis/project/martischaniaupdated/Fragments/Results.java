package martis.project.martischaniaupdated.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.text.DecimalFormat;

import martis.project.martischaniaupdated.Damage;
import martis.project.martischaniaupdated.R;

/**
 * Created by GAK on 4/3/2016.
 */
public class Results extends Fragment {
    Runnable runnable;
    float[] data;
    ProgressBar myProgress;
    ProgressBar dangerSkinProgress;
    TextView heartTextView;
    TextView tempTextView;
    TextView tempOutText;
    TextView dehydration2;
    Damage potentialDamage ;
    AlertDialog.Builder  timeSet;
    //  int flag=1;
    int flag25;
    int flag50;
    int flag75;
    int flag100;
    AlertDialog.Builder spfSettings;
    float time;

    int age;
    int skinType;
    int UVRad;

    int GSRCoefficient = 200;
    static  int spf;
    int BPM = 77;
    float Temp = (float) 28;
    float outTemp = (float) 21.5;
    float GSR = 400;








    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results,null);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Project M.A.R.T.I.S.");

        /*ImageView heart = (ImageView) view.findViewById(R.id.heartImage);
        TextView bpm = (TextView) view.findViewById(R.id.bpm);
        ImageView bodyTemp = (ImageView) view.findViewById(R.id.bodyTempImage);
        TextView bodyTempText = (TextView) view.findViewById(R.id.bodyTemp);
        ImageView outTemp = (ImageView) view.findViewById(R.id.outTempImage);
        TextView outTempText = (TextView) view.findViewById(R.id.outTemp);
        ImageView dehydration = (ImageView) view.findViewById(R.id.dehydrationImage);
        TextView dehydrationText = (TextView) view.findViewById(R.id.dehydration);*/
        flag25 = 1;
        flag50 = 1;
        flag75 = 1;
        flag100 = 1;
        dangerSkinProgress = (ProgressBar) view.findViewById(R.id.dangerskin);
        dangerSkinProgress.setProgress(0);
        SharedPreferences userDetails = Results.this.getActivity().getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        String Sage = userDetails.getString("ageInput", "3");
        // int age;
        if (Sage == "") {                                                              // --Passing Data from Settings.java START
            age = 1;
        } else {
            age = Integer.parseInt(Sage);
            //flag = 0;
        }
        String Sweight = userDetails.getString("weightInput", "3");
        int weight1;
        if (Sweight == "") {
            weight1 = 1;
        } else {
            weight1 = Integer.parseInt(Sweight);
            // flag = 0;

        }
        float weight = weight1;

        String Sheight = userDetails.getString("heightInput", "2");
        int height1;
        if (Sheight == "") {
            height1 = 1;
        } else {
            height1 = Integer.parseInt(Sheight);
            // flag = 0;
        }
        float height = height1 * ((float) 0.01);
        String Sskin = userDetails.getString("skinInput", "5");
        skinType = Integer.parseInt(Sskin);
        String SUV = userDetails.getString("UVInput", "5");
        // int UVRad;
        if (SUV == "") {
            UVRad = 1;
        } else {
            UVRad = Integer.parseInt(SUV);
        }
        float BMI = weight / (height * height);              // --Passing Data from Settings.java END
        //  data = readFromFile("currData");
        //  int UVRad = (int) data[0];
        //int BPM = (int) data[1];                          // Data transfer from Bluetooth
        //  float Temp = data[2];
        //float outTemp = data[3];
        //float GSR= data[4];
        String sBPM;
        if (BPM < 0) {                                    // When arduino doesnt send BPM (sends -1), store them as 0.
            sBPM = "0";
        } else {
            sBPM = Integer.toString(BPM);
        }

        myProgress = (ProgressBar) view.findViewById(R.id.progressBar);     // UI Interaction. Sets Progress bar for radiation
        myProgress.setProgress(UVRad);


        heartTextView = (TextView) view.findViewById(R.id.bpm);  // UI Interaction. Sets BPM
        heartTextView.setText(sBPM);

        tempTextView = (TextView) view.findViewById(R.id.bodyTemp);       // UI Interaction. Sets skinTemperature
        tempTextView.setText(Temp + " °C");

        tempOutText = (TextView) view.findViewById(R.id.outTemp);  // UI Interaction. Sets environment temperature
        tempOutText.setText(outTemp + " °C");

        float fluids = (float) (-1.95403 + (0.0554441 * BMI) - (0.0228502 * Temp) + (0.0084186 * BPM) + 0.000370397 * (GSRCoefficient + GSR)); //Experimental algorithm for fluid loss calc
        float dehydration1 = (100 * (1 - (weight - fluids) / weight)); // convert to %
        DecimalFormat df = new DecimalFormat("###.###");
        String deh = (df.format(-dehydration1));
        dehydration2 = (TextView) view.findViewById(R.id.dehydration);           // UI Interaction. Sets dehydration
        dehydration2.setText(deh + "%");
        ambArm(Temp, outTemp);                                  // Function, compares 2 temps and shows if there is a chance of being sick

       /* TextView textAge = (TextView) view.findViewById(R.id.ageView);
        TextView textWeight = (TextView) view.findViewById(R.id.weightView);           //UI Interaction, shows user settings
        TextView textHeight = (TextView) view.findViewById(R.id.heightView);
        TextView textSkin = (TextView) view.findViewById(R.id.skinView);
        textAge.setText("Age: "+age+" years old");
        textWeight.setText("Weight: " + weight1 + "kg");
        textHeight.setText("Height(m.cm): "+height1);
        textSkin.setText("Skin Type: "+skinType+" (FitzPatrick)"); */

        dangerSkinProgress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setTime();
            }
        });






        return view;
    }
    private void setTime() {
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        timeSet = new AlertDialog.Builder(getActivity());
        timeSet.setView(input);
        timeSet.setTitle("SetTime");
        timeSet.setMessage("Input expected time exposed to the sun");


        timeSet.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                time = Float.parseFloat(input.getText().toString());
                Log.i("Error", time + "       ok button");
                potentialDamage = new Damage(age, skinType, UVRad, time);
                int dmg = (int) potentialDamage.skinDamage();
                dangerSkinProgress.setProgress(dmg);
                if (dmg >= 25) {
                    float spfExposure = 4 * time;
                    spf = (int) (spfExposure / potentialDamage.maxTimeExposure * ((2 - (time / 120))));
                    potentialDamage.spf = spf;
                    dmg = (int) potentialDamage.skinDamage();
                    dangerSkinProgress.setProgress(dmg);
                    Toast.makeText(Results.this.getActivity(), "Apply suncream of at least" + spf + " volume immediately", Toast.LENGTH_SHORT).show();
                  //  TextView textSpf = (TextView) Results.this.getActivity().findViewById(R.id.spfView);
                   //       textSpf.setText("Last Suncream SPF: " + spf);

                }


            }
        });
        timeSet.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                time = 10;
                dangerSkinProgress.setProgress(10);
                dialog.cancel();

                Log.i("Error", time + "       cancel button");
            }
        });
        timeSet.show();
    }
    private float[] readFromFile(String fileName) {          //Pass data from textfile to variables. Designed for 5 slot float table
        FileInputStream fileInput;
        String dataToRead = "";
        float[] floatData = new float[5];
        int index = 0;

        try {
            fileInput = getActivity().openFileInput(fileName);

            while (fileInput.available() != 0) {
                char data = (char) fileInput.read();

                if (data != ' ' && data != '\n') {
                    dataToRead += data;
                } else {
                    floatData[index] = Float.parseFloat(dataToRead);
                    Log.i("Error", "Data read " + dataToRead);
                    dataToRead = "";
                    index++;
                }
            }

        } catch (Exception e) {
            Log.i("Error", e.getMessage());
            floatData = new float[5];
            return floatData;
        }

        if (floatData == null) {
            floatData = new float[5];
        }

        return floatData;
    }
    public void ambArm (float armTemp,float ambTemp){  //Calculates and informs user when he's sick
        float armTempTheory;
        if (ambTemp<23){
            armTempTheory= (float) (0.565*ambTemp+17);
        }else if (ambTemp>=23&&ambTemp<34){
            armTempTheory= (float) (0.455*ambTemp+19.55);
        }else {
            armTempTheory= (float) (0.4*ambTemp+21.4);
        }
        double error;
        error=(armTempTheory-armTemp)/armTempTheory;
        if (ambTemp<30){
            if (error<0.15&&error>-0.15){
                //healthy
            }else if(error>=0.15){
                //      Toast.makeText(Results.this, "Careful, danger of flu", Toast.LENGTH_SHORT).show();
            }else if(error<=0.15){
                //     Toast.makeText(Results.this, "You should consider putting more clothes", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (error<0.1&&error>-0.1){
                //healthy
            }else if(error>=0.1){
                //  Toast.makeText(Results.this, "Careful, danger of flu", Toast.LENGTH_SHORT).show();
            }else if(error<=0.1){
                //   Toast.makeText(Results.this, "You should consider putting more clothes", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
