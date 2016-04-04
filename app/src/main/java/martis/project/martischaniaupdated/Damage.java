package martis.project.martischaniaupdated;

import android.util.Log;

/**
 * Created by GAK on 4/4/2016.
 */
public class Damage {
    //CUSTOM USER INPUT
    public int spf;
    private int age1;
    private int skinType1;
    private int UVRad1;
    private float time1;
    public float maxTimeExposure;



    public Damage(int age,int skin,int UV,float time){
        age1=age;
        skinType1=skin;
        UVRad1=UV;
        time1 = time;
        Log.i("Error", time1 + "  Damage.time1");
        //  EditText input = new EditText(this);

       /* AlertDialog.Builder timeSet = new AlertDialog.Builder(getApplicationContext());


        timeSet.setTitle("SetTime");
        timeSet.setMessage("Input expected time exposed to the sun");
        timeSet.setView(input);
        timeSet.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                time = Float.parseFloat(input.getText().toString());
            }
        });
        timeSet.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                time = 0;
                dialog.cancel();
            }
        });
        timeSet.show();*/
    }

    public float skinDamage() {
        float skinDmg;
        int sunScreenEndurance = 120;
        if (UVRad1 == 0) {
            UVRad1 = 1;
        }
        if (age1 <= 12) {
            skinType1 = 1;
        }
        if (skinType1 == 1) {
            maxTimeExposure = 67 / (UVRad1);
            Log.i("Error", String.valueOf(maxTimeExposure)+"random");
        } else {
            Log.i("Error", skinType1+" skintone");
            maxTimeExposure = ((skinType1 - 1) * 100) / (UVRad1);
            Log.i("Error", String.valueOf(maxTimeExposure)+"randomtiem");

        } //20
        if (spf != 0) {
            //Toast.makeText(Results.this, "SPF Detected", Toast.LENGTH_SHORT).show();
            if (time1 <= sunScreenEndurance) {
                maxTimeExposure = maxTimeExposure * spf * ((2 - (time1 / sunScreenEndurance)) / 2);//83,3
            }
        }
        skinDmg = ((time1 / maxTimeExposure) * 100);
        //  Toast.makeText(Results.this, skinDmg + "skin Damage", Toast.LENGTH_SHORT).show();
        return skinDmg;
    }
}
