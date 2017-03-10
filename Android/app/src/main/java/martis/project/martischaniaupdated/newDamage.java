package martis.project.martischaniaupdated;

import android.util.Log;

/**
 * Created by GAK on 4/9/2016.
 */
public class newDamage {

    public int spf;
    private int age1;
    private int skinType1;
    private int UVRad1;
    private float time1;
    public float unprotectedTime;
    public float protectedTime;
    public float maxTimeExposure;

    public newDamage(int age, int skin, int UV, float time, int spf) {
        age1 = age;
        skinType1 = skin;
        UVRad1 = UV;
        time1 = time;
        this.spf = spf;
    }

    public float skinDamage() {
        float skinDmg=0;
        int sunScreenEndurance = 120;
        if (UVRad1 == 0) {
            UVRad1 = 1;
        }
        if (age1 <= 12) {
            skinType1 = 1;
        }
        /****
         *
         * unProtected time = o xronos pou to antiliako den exei allo isxu/den exei topothetithei
         * protected time = o xronos kata ton opoio to antiliako einai energo
         * an kseperasei to time1 to xrono pou diarkei to antiliako, tote o ypoloipos xronos, ginetai unprotected
         *
         */
        float maxTime;
        if (skinType1 == 1) {
            unprotectedTime = 67 / (UVRad1);
            maxTime= unprotectedTime;
        }
        else {
            unprotectedTime = ((skinType1 - 1) * 100) / (UVRad1);
            maxTime = unprotectedTime;
        }
        if (spf==0){
            maxTimeExposure=unprotectedTime;
        } else {
            if (time1>=sunScreenEndurance) {
                    protectedTime= (unprotectedTime*(spf/2));// 120/2 apto trigwno fade tou spf
                    skinDmg= (sunScreenEndurance/protectedTime)*100;
                    unprotectedTime=time1-sunScreenEndurance;
            }
            else{
                protectedTime= unprotectedTime*spf*((2-(time1/sunScreenEndurance))/2);
                unprotectedTime=0;
                skinDmg=(sunScreenEndurance/protectedTime)*100;
            }
            maxTimeExposure = protectedTime + unprotectedTime;
            Log.i("Error","maxTimeExposure = "+maxTimeExposure);
            Log.i("Error","protectedTime = "+protectedTime);
            Log.i("Error","unprotectedTime = "+unprotectedTime);

        }
            skinDmg+=(unprotectedTime/maxTime)*100;
       // skinDmg = unprotectedDmg + protectedDmg;
       // skinDmg= ((time1/maxTimeExposure)*100);
        Log.i("Error","Damage = "+skinDmg);



        return skinDmg;
    }


}
