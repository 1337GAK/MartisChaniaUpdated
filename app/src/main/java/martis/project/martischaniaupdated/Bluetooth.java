package martis.project.martischaniaupdated;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

import martis.project.martischaniaupdated.Fragments.Results;


public class Bluetooth  extends BlunoLibrary{
    private Button buttonScan;
    private Button buttonSerialSend;
    private TextView tips;
    private TextView connectionStatus;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTheme(android.R.style.Theme_Holo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        onCreateProcess();														//onCreate Process by BlunoLibrary


        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        connectionStatus = (TextView) findViewById(R.id.connectionStatus);
        connectionStatus.setText("Connection :");

        tips = (TextView) findViewById(R.id.tips);
        Random rand = new Random();
        if ( rand.nextBoolean()) {
            tips.setText("Always remember to use common sense before technology");
        } else {
            tips.setText("Your MARTIS device should always face the sun when measuring!");
        }

        buttonSerialSend = (Button) findViewById(R.id.buttonSerialSend);		//initial the button for sending the data
        buttonSerialSend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //send the data to the BLUNO
                serialSend("m");
                Toast.makeText(Bluetooth.this, "Prepare your martis device for measurements", Toast.LENGTH_SHORT).show();
            }
        });


        buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blankmenu, menu);

        return true;
    }

        protected void onResume(){
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();														//onResume Process by BlunoLibrary
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }

    protected void onStop() {
        super.onStop();
        onStopProcess();														//onStop Process by BlunoLibrary


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

    @Override
    public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
        switch (theConnectionState) {											//Four connection state
            case isConnected:
                serialSend("c");
                connectionStatus.setText("Connection : Connected");
                break;
            case isConnecting:
                connectionStatus.setText("Connection : Connecting");
                break;
            case isToScan:
                connectionStatus.setText("Connection : ");
                break;
            case isScanning:
                connectionStatus.setText("Connection : Scanning");
                break;
            case isDisconnecting:
                connectionStatus.setText("Connection : Disconnecting");
                break;
            default:
                connectionStatus.setText("Connection : ");
                break;
        }
    }

    @Override
    public void onSerialReceived(String theString) {							//Once connection data received, this function will be called
        // TODO Auto-generated method stub

        Toast.makeText(Bluetooth.this, "Info "+theString, Toast.LENGTH_SHORT).show();
       SharedPreferences savedData = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor edit = savedData.edit();
        Log.i("Error", "User Data= " + theString);
        edit.putString("userData", theString);
        edit.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}