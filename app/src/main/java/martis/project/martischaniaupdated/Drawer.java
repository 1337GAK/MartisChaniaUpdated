package martis.project.martischaniaupdated;

import android.Manifest;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.annotation.Target;
import java.util.Set;
import java.util.UUID;

import co.lujun.lmbluetoothsdk.BluetoothLEController;
import co.lujun.lmbluetoothsdk.base.BluetoothLEListener;
import co.lujun.lmbluetoothsdk.base.BluetoothListener;
import martis.project.martischaniaupdated.Fragments.AboutUs;
import martis.project.martischaniaupdated.Fragments.Bluetooth;
import martis.project.martischaniaupdated.Fragments.Donate;
import martis.project.martischaniaupdated.Fragments.History;
import martis.project.martischaniaupdated.Fragments.RateFeedback;
import martis.project.martischaniaupdated.Fragments.Results;
import martis.project.martischaniaupdated.Fragments.Settings;

public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;

    BluetoothLEController mBLEController;
    String macAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hview = navigationView.getHeaderView(0);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new Results(),"RESULTS").commit();



        mBLEController = BluetoothLEController.getInstance().build(this);

        mBLEController.setBluetoothListener(new BluetoothLEListener() {

            @Override
            public void onReadData(final BluetoothGattCharacteristic characteristic) {
                // Read data from BLE device

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = characteristic.getStringValue(0);
                        Log.i("BLE","Read data " + data);
                    }
                });
            }

            @Override
            public void onWriteData(final BluetoothGattCharacteristic characteristic) {
                // When write data to remote BLE device, the notification will send to here.
                String data = characteristic.getStringValue(0);
                Log.i("BLE","Write data " + data);
            }

            @Override
            public void onDataChanged(final BluetoothGattCharacteristic characteristic) {
                // When data changed, the notification will send to here.
                Log.i("BLE","Data changed " + characteristic.getStringValue(0));
            }

            @Override
            public void onActionStateChanged(int preState, int state) {
                // Callback when bluetooth power state changed.
                Log.i("BLE","State changed from " + preState + " to " + state);
            }

            @Override
            public void onActionDiscoveryStateChanged(String discoveryState) {
                // Callback when local Bluetooth adapter discovery process state changed.
                Log.i("BLE", "Discovery state changed " + discoveryState);
            }

            @Override
            public void onActionScanModeChanged(int preScanMode, int scanMode) {
                // Callback when the current scan mode changed.
                Log.i("BLE", "Action scan mode changed from " + preScanMode +"to "+scanMode);
            }



            @Override
            public void onBluetoothServiceStateChanged(final int state) {
                // Callback when the connection state changed.
                Log.i("BLE", "Bluetooth serivce state changed to " + state);
            }

            @Override
            public void onActionDeviceFound(final BluetoothDevice device, short rssi) {
                // Callback when found device.
                Log.i("BLE","Device found " + device.getName() + " "+rssi);
            }
        });



        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 69);





    }


    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                Toast.makeText(getBaseContext(), R.string.back, Toast.LENGTH_SHORT).show();
            }
        }

        mBackPressed = System.currentTimeMillis();
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); ++i) {
                getFragmentManager().popBackStack();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        AboutUs aboutHelp = (AboutUs) getFragmentManager().findFragmentByTag("ABOUT");
        RateFeedback rateHelp = (RateFeedback) getFragmentManager().findFragmentByTag("RATE");
        Settings settingsHelp = (Settings) getFragmentManager().findFragmentByTag("SETTINGS");
        Results resultsHelp = (Results) getFragmentManager().findFragmentByTag("RESULTS");
       getMenuInflater().inflate(R.menu.drawer, menu);
     /*   if ((aboutHelp != null && aboutHelp.isVisible()) || (rateHelp!= null && rateHelp.isVisible())) { TODO FRAGMENT MENU CHANGE
            menu.findItem(R.id.action_refresh).setVisible(false);
            menu.findItem(R.id.help).setVisible(false);
        }else if  (settingsHelp != null && settingsHelp.isVisible()){
            menu.findItem(R.id.action_refresh).setVisible(false);
        } */


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            Settings settingsHelp = (Settings) getFragmentManager().findFragmentByTag("SETTINGS");
            Results resultsHelp = (Results) getFragmentManager().findFragmentByTag("RESULTS");
            Donate donateHelp = (Donate) getFragmentManager().findFragmentByTag("DONATE");
            AboutUs aboutHelp = (AboutUs) getFragmentManager().findFragmentByTag("ABOUT");
            RateFeedback rateHelp = (RateFeedback) getFragmentManager().findFragmentByTag("RATE");
            Bluetooth bluetoothHelp = (Bluetooth) getFragmentManager().findFragmentByTag("BLUETOOTH");
            History historyHelp = (History) getFragmentManager().findFragmentByTag("HISTORY");

            if (settingsHelp != null && settingsHelp.isVisible()) {
                        settingsHelp.settingsAHelp();

            } else if (resultsHelp != null && resultsHelp.isVisible()) {
                        resultsHelp.resultsAHelp();          }
                else if (donateHelp != null && donateHelp.isVisible()) {
                    Toast.makeText(Drawer.this, "DONATE HELP", Toast.LENGTH_SHORT).show();
                } else if (aboutHelp != null && aboutHelp.isVisible()) {
                    Toast.makeText(Drawer.this, "ABOUT HELP", Toast.LENGTH_SHORT).show();
                } else if (rateHelp != null && rateHelp.isVisible()) {
                    Toast.makeText(Drawer.this, "RATE HELP", Toast.LENGTH_SHORT).show();
                } else if (bluetoothHelp != null && bluetoothHelp.isVisible()) {
                    Toast.makeText(Drawer.this, "BLUETOOTH HELP", Toast.LENGTH_SHORT).show();
                } else if (historyHelp != null && historyHelp.isVisible()) {
                    Toast.makeText(Drawer.this, "HISTORY HELP", Toast.LENGTH_SHORT).show();
                }


                return true;
            } else if (id == R.id.action_refresh) {
                Toast.makeText(Drawer.this, "Refresh .java", Toast.LENGTH_SHORT).show();
                String a = "55";
                mBLEController.write(a.getBytes());
            }

            return super.onOptionsItemSelected(item);
        }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getFragmentManager();

        if (id == R.id.settings) {

            fm.beginTransaction().replace(R.id.content_frame, new Settings(),"SETTINGS").addToBackStack(null).commit();

        } else if (id == R.id.results) {
            fm.beginTransaction().replace(R.id.content_frame, new Results(),"RESULTS").commit();

        } else if (id == R.id.about) {
            fm.beginTransaction().replace(R.id.content_frame, new AboutUs(),"ABOUT").addToBackStack(null).commit();
        } /*else if (id == R.id.donate) {
            fm.beginTransaction().replace(R.id.content_frame, new Donate(),"DONATE").addToBackStack(null).commit();
        } else if (id == R.id.history) {

            fm.beginTransaction().replace(R.id.content_frame, new History(),"HISTORY").addToBackStack(null).commit();
        }*/
            else if (id == R.id.bluetooth) {
            //fm.beginTransaction().replace(R.id.content_frame, new Bluetooth(),"BLUETOOTH").addToBackStack(null).commit();

            if (mBLEController.getConnectionState() != BluetoothAdapter.STATE_CONNECTED) {
                //Check if bluetooth is on
                if (mBLEController.isEnabled()) {
                    if (mBLEController.isSupportBLE()) {
                        if (mBLEController.isAvailable()) {
                            while (mBLEController.getBluetoothState() != BluetoothAdapter.STATE_ON) {
                                //TODO :Add timeout timer
                            }


                            if (mBLEController.startScan()) {
                                Toast.makeText(Drawer.this, "Scan started", Toast.LENGTH_SHORT).show();

                                Set<BluetoothDevice> devices = mBLEController.getBondedDevices();

                                for (BluetoothDevice i : devices) {
                                    //Log.i("BLE", i.getName());
                                    if (i.getName().equals("Bluno")) {
                                        macAdress = i.getAddress();
                                        //Log.i("BLE", macAdress);
                                    }
                                }

                                //TODO :Choose your own device
                                mBLEController.connect(macAdress);
                                while (mBLEController.getConnectionState() != BluetoothAdapter.STATE_CONNECTED) {

                                }

                            }
                            //mBLEController.cancelScan();

                        } else {
                            Toast.makeText(Drawer.this, "Bluetooth is not Available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Drawer.this, "Device does not support Bluetooth LE", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Drawer.this, "Bluetooth is not Enabled", Toast.LENGTH_SHORT).show();
                }
            }




        } else if (id == R.id.rate) {
            fm.beginTransaction().replace(R.id.content_frame, new RateFeedback(),"RATE").addToBackStack(null).commit();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mBLEController.getConnectionState() == BluetoothAdapter.STATE_CONNECTED) {
            //mBLEController.disconnect();
            mBLEController.release();
        }
    }

    @Override
    public void onPause(){
        super.onPause();

        if(mBLEController.getConnectionState() == BluetoothAdapter.STATE_CONNECTED) {
            //mBLEController.disconnect();
            mBLEController.release();
        }



    }

    @Override
    public void onResume() {
        super.onResume();

        if(mBLEController.getConnectionState() == BluetoothAdapter.STATE_DISCONNECTED) {
            if (mBLEController.startScan()) {
                //Log.i("BLE", "Reconnect onResume");
                mBLEController.reConnect();
            }
            //mBLEController.cancelScan();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        //mBLEController.disconnect();
    }

    @Override
    public void onRestart() {
        super.onRestart();

        if(mBLEController.getConnectionState() == BluetoothAdapter.STATE_DISCONNECTED) {
            if (mBLEController.startScan()) {
                mBLEController.reConnect();
            }
            //mBLEController.cancelScan();
        }

    }




}