package martis.project.martischaniaupdated;

import android.Manifest;
import android.app.FragmentManager;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
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
                // Read data from BLE device.
            }

            @Override
            public void onWriteData(final BluetoothGattCharacteristic characteristic) {
                // When write data to remote BLE device, the notification will send to here.
            }

            @Override
            public void onDataChanged(final BluetoothGattCharacteristic characteristic) {
                // When data changed, the notification will send to here.
            }

            @Override
            public void onActionStateChanged(int preState, int state) {
                // Callback when bluetooth power state changed.
            }

            @Override
            public void onActionDiscoveryStateChanged(String discoveryState) {
                // Callback when local Bluetooth adapter discovery process state changed.
            }

            @Override
            public void onActionScanModeChanged(int preScanMode, int scanMode) {
                // Callback when the current scan mode changed.
            }

            @Override
            public void onBluetoothServiceStateChanged(final int state) {
                // Callback when the connection state changed.
            }

            @Override
            public void onActionDeviceFound(final BluetoothDevice device, short rssi) {
                // Callback when found device.
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
        getMenuInflater().inflate(R.menu.drawer, menu);
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
                Toast.makeText(Drawer.this, "SETTINGS HELP", Toast.LENGTH_SHORT).show();

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
        }*/ else if (id == R.id.history) {
            fm.beginTransaction().replace(R.id.content_frame, new History(),"HISTORY").addToBackStack(null).commit();
        } else if (id == R.id.bluetooth) {
            //fm.beginTransaction().replace(R.id.content_frame, new Bluetooth(),"BLUETOOTH").addToBackStack(null).commit();

            if(mBLEController.startScan()) {
                System.out.print("Scan started");

                mBLEController.connect("C8:A0:30:F6:E2:C5");

            }


        } else if (id == R.id.rate) {
            fm.beginTransaction().replace(R.id.content_frame, new RateFeedback(),"RATE").addToBackStack(null).commit();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}