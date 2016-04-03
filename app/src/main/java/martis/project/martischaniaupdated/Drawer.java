package martis.project.martischaniaupdated;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import martis.project.martischaniaupdated.Fragments.AboutUs;
import martis.project.martischaniaupdated.Fragments.Bluetooth;
import martis.project.martischaniaupdated.Fragments.Donate;
import martis.project.martischaniaupdated.Fragments.History;
import martis.project.martischaniaupdated.Fragments.RateFeedback;
import martis.project.martischaniaupdated.Fragments.Results;
import martis.project.martischaniaupdated.Fragments.Settings;

public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        fm.beginTransaction().replace(R.id.content_frame, new Results()).commit();

        TextView user = (TextView) hview.findViewById(R.id.userName);
        user.setText(R.string.name);
        TextView email = (TextView) hview.findViewById(R.id.email);
        email.setText( R.string.email);


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
            Toast.makeText(Drawer.this, "Help yourself 'tard", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.action_refresh){
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

            fm.beginTransaction().replace(R.id.content_frame, new Settings()).addToBackStack(null).commit();

        } else if (id == R.id.results) {

            fm.beginTransaction().replace(R.id.content_frame, new Results()).commit();
        } else if (id == R.id.about) {
            fm.beginTransaction().replace(R.id.content_frame, new AboutUs()).addToBackStack(null).commit();
        } else if (id == R.id.donate) {
            fm.beginTransaction().replace(R.id.content_frame, new Donate()).addToBackStack(null).commit();
        } else if (id == R.id.history) {
            fm.beginTransaction().replace(R.id.content_frame, new History()).addToBackStack(null).commit();
        } else if (id == R.id.bluetooth) {
            fm.beginTransaction().replace(R.id.content_frame, new Bluetooth()).addToBackStack(null).commit();
        } else if (id == R.id.rate) {
            fm.beginTransaction().replace(R.id.content_frame, new RateFeedback()).addToBackStack(null).commit();



            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
