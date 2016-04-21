package martis.project.martischaniaupdated.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileInputStream;
import java.text.DecimalFormat;

import martis.project.martischaniaupdated.Damage;
import martis.project.martischaniaupdated.R;
import martis.project.martischaniaupdated.newDamage;

/**
 * Created by GAK on 4/3/2016.
 */
public class Results extends Fragment {


    Runnable runnable;


    float[] data;
    static public ProgressBar myProgress;
    ProgressBar dangerSkinProgress;
    String readings;

    TextView showExtras;
    TextView heartTextView;
    TextView tempTextView;
    TextView tempOutText;
    TextView dehydration2;
    newDamage potentialDamage ;
    AlertDialog.Builder  timeSet;
    AlertDialog.Builder waterInput;
    float water1;
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
    /* static  int spf; */
    int BPM ;
    float Temp;
    float outTemp;
    float GSR;







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
       // String SUV = userDetails.getString("UVInput", "5");


        //showExtras = (TextView) view.findViewById(R.id.extras);

        //showExtras.setText(readings);
        // int UVRad;

        SharedPreferences savedData = Results.this.getActivity().getSharedPreferences("data" , Context.MODE_PRIVATE);
        String readings = savedData.getString("userData","0832.522.10600085");
        //String readings = "0832.522.11234120";


        String SUV = readings.substring(0,2);
        String outterTemp = readings.substring(2,6);
        String temp =readings.substring(6,10);
        String gsr = readings.substring(10,14);
        String bpm = readings.substring(14,17);

        BPM = Integer.parseInt(bpm);
        Temp = Float.parseFloat(temp);
        outTemp = Float.parseFloat(outterTemp);
        GSR = Integer.parseInt(gsr);
        //Log.i("Error", "gak " + SUV +" "+ outterTemp +" "+ temp+" " + gsr+" " + bpm );






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
        heartTextView.setText(sBPM+ " BPM");

        tempTextView = (TextView) view.findViewById(R.id.bodyTemp);       // UI Interaction. Sets skinTemperature
        tempTextView.setText(Temp + " °C");

        tempOutText = (TextView) view.findViewById(R.id.outTemp);  // UI Interaction. Sets environment temperature
        tempOutText.setText(outTemp + " °C");
        float fluids = (float) (-1.95403 + (0.0554441 * BMI) - (0.0228502 * Temp) + (0.0084186 * BPM) + 0.000370397 * (GSRCoefficient + GSR)); //Experimental algorithm for fluid loss calc
        dehydration2 = (TextView) view.findViewById(R.id.dehydration);
        Log.i("Error","Water before dehydration calculation = "+water1);
        float dehydration1 = (100 * (1 - (weight - fluids + water1) / weight)); // convert to %
        final float staticDeh = dehydration1;
        DecimalFormat df = new DecimalFormat("###.###");
        String deh = (df.format(dehydration1));
        dehydration2.setText(deh + "%");// UI Interaction. Sets dehydration
        dehydration2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout waterLayout = new LinearLayout(getActivity());
                waterLayout.setOrientation(LinearLayout.VERTICAL);
                final EditText water = new EditText(getActivity());
                water.setInputType(InputType.TYPE_CLASS_NUMBER);
                waterLayout.addView(water);

                waterInput = new AlertDialog.Builder(getActivity());
                waterInput.setView(waterLayout);
                waterInput.setTitle("Water Input");
                waterInput.setMessage("Input amount of water you drank");
                waterInput.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        water1 = Float.parseFloat(water.getText().toString());
                        Log.i("Error","Water input = "+water1);
                        dehydration2.setText(staticDeh+"%");
                    }
                });
                waterInput.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

            waterInput.show();
            }
        });

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
        LinearLayout box = new LinearLayout(getActivity());
        box.setOrientation(LinearLayout.VERTICAL);

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Set time");
        box.addView(input);

        final EditText spfinput = new EditText(getActivity());
        spfinput.setHint("Set SPF");
        spfinput.setInputType(InputType.TYPE_CLASS_NUMBER);
        box.addView(spfinput);


        timeSet = new AlertDialog.Builder(getActivity());
        timeSet.setView(box);
        timeSet.setTitle("SetTime");
        timeSet.setMessage("First dialog box:Input expected time exposed to the sun" +
                "\nSecond dialog box:Input your suncream's sun protection factor");

        timeSet.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String t = input.getText().toString();
                if (t.equals("")) {
                    t = "1";
                }
                time = Float.parseFloat(t);
                String spf123 = spfinput.getText().toString();
                if (spf123.equals("")) {
                    spf123 = "0";
                }
                int spfText = Integer.parseInt(spf123);
                    Log.i("Error", time + "       ok button");
                potentialDamage = new newDamage(age, skinType, UVRad, time, spfText);
                int dmg;
               // savedDmg(dmg);
                dmg = (int) potentialDamage.skinDamage();

                dangerSkinProgress.setProgress(dmg);
                if (spfText <= 10 && dmg >= 70){
                    Toast.makeText(Results.this.getActivity(), "Consider putting on suncream with spf.", Toast.LENGTH_SHORT).show();
                }else if (spfText >= 30 && dmg >= 70){
                    Toast.makeText(Results.this.getActivity(), "You should expose yourself to the sun for less time.", Toast.LENGTH_SHORT).show();
                }else if (dmg <=40){
                    Toast.makeText(Results.this.getActivity(), "You are perfectly safe from the sun's rays", Toast.LENGTH_SHORT).show();
                }else if(dmg >40 && dmg <=70 ){

                }
                /*if (dmg >= safePercent) {
                    float spfExposure = (safePercent/100) * time;
                    spf = (int) (spfExposure / potentialDamage.maxTimeExposure * ((2 - (time / 120))));
                    potentialDamage.spf = spf;
                    dmg = (int) potentialDamage.skinDamage();
                    dangerSkinProgress.setProgress(dmg);
                    Toast.makeText(Results.this.getActivity(), "Apply suncream of at least" + spf + " volume immediately", Toast.LENGTH_SHORT).show();  //  TextView textSpf = (TextView) Results.this.getActivity().findViewById(R.id.spfView);
                    //       textSpf.setText("Last Suncream SPF: " + spf);

                }*/


            }
        });
        timeSet.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
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
                Toast.makeText(Results.this.getActivity(),"No potential danger detected", Toast.LENGTH_SHORT).show();
            }else if(error>=0.15){
                      Toast.makeText(Results.this.getActivity(), "Careful, danger of flu", Toast.LENGTH_SHORT).show();
            }else if(error<=0.15){
                     Toast.makeText(Results.this.getActivity(), "You should consider putting more clothes", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (error<0.1&&error>-0.1){
                Toast.makeText(Results.this.getActivity(),"No potential danger detected", Toast.LENGTH_SHORT).show();
            }else if(error>=0.1){
                  Toast.makeText(Results.this.getActivity(), "Careful, danger of flu", Toast.LENGTH_SHORT).show();
            }else if(error<=0.1){
                   Toast.makeText(Results.this.getActivity(), "You should consider putting more clothes", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void resultsAHelp(){

        LayoutInflater layoutInflater= LayoutInflater.from(Results.this.getActivity());
        View promptView =layoutInflater.inflate(R.layout.pop_up_results_help, null);

        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(Results.this.getActivity());
        alertDialogBuilder.setView(promptView);

        WebView helpText = (WebView) promptView.findViewById(R.id.resultsHelp);
        //helpText.setText(R.string.resultshelp);
       // helpText.setBackgroundColor(Color.TRANSPARENT);

         final String htmlText ="<body style=\"text-align:justify\">" + "<h1><b>Results Help:</b></h1>\n\n" +
        "    <h2><b>UV Exposure bar:</b></h2>\n\n" +
                "        This bar shows the index value of the sun\'s UltraViolet rays, ranges from 1-11.\n" +
                "        Be careful for values above 6.\n\n" +
                "\n" +
                "    <h2><b>Skin Danger bar:</b></h2> \n\n" +
                "        This bar shows the percentage of the damage your skin will take when exposed to\n" +
                "    the sun for X minutes under Y SPF.\n\n\n\n" +
                "\n" +
                "  <h2> <b> Heartrate:</b></h2>\n Shows your heartbeats per minute at the time of the readings.\n" +
                "   <h2><b>Body Temperature:</b></h2> \nShows your wrist\'s temperature.\n\n\n\n" +
                "   <h2><b> Outside Temperature: </b></h2>\nShows ambient temperature.\n" +
                "   <h2> <b>Dehydration:</b></h2> Shows the amount of fluids you have lost. Be careful for values above 1-2%.</body>";
       // helpText.setText(Html.fromHtml(htmlText));

        helpText.loadData(htmlText, "text/html", "utf-8");
        helpText.setBackgroundColor(Color.TRANSPARENT);

        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        promptView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }
    /*
    public float savedDmg(float dmg){
        SharedPreferences dmgPrefs = Results.this.getActivity().getSharedPreferences("dmgPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editDmg = dmgPrefs.edit();
        editDmg.putFloat("dmg",dmg);

        float totalDmg = dmg+prevDmg;
        return totalDmg;
    }*/


}
