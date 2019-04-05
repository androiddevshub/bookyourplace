package com.app.bookyourplace.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bookyourplace.Model.DrawerNavItem;
import com.app.bookyourplace.R;
import com.app.bookyourplace.Utils.PrefUtils;
import com.app.bookyourplace.View.Adapters.DrawerItemCustomAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private PrefUtils prefUtils;
    private ListView mDrawerList;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView textViewName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        prefUtils = new PrefUtils(this);
        navigationView = findViewById(R.id.nav_view);

        toolbar = findViewById(R.id.toolbarExplore);
        setSupportActionBar(toolbar);
        mDrawerList = findViewById(R.id.nav_drawer_list_view);
        drawerLayout = findViewById(R.id.drawerHome);
        textViewName = findViewById(R.id.name_of_user);

        DrawerNavItem[] drawerNavItems =  new DrawerNavItem[4];


        drawerNavItems[0] = new DrawerNavItem(R.drawable.ic_explore, "Explore");
        drawerNavItems[1] = new DrawerNavItem(R.drawable.ic_home, "Home");
        drawerNavItems[2] = new DrawerNavItem(R.drawable.ic_booking, "Bookings");
        drawerNavItems[3] = new DrawerNavItem(R.drawable.ic_profile, "Profile");

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_nav_item_row, drawerNavItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {
            //We can perform a particular action when the
            // Navigation View is opened by overriding the
            // onDrawerOpened() method.
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //Toast.makeText(getApplicationContext(),"Drawer Opened",Toast.LENGTH_SHORT).show();
            }

            //We can perform a particular action when the
            // Navigation View is closed by overriding the
            // onDrawerClosed() method.
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //Toast.makeText(getApplicationContext(),"Drawer Closed",Toast.LENGTH_SHORT).show();
            }
        };

        HashMap<String, String> session = prefUtils.getUserDetails();
        String name = session.get(PrefUtils.KEY_EMAIL);

        textViewName.setText("Hi! "+ name);

        //Finally setting up the drawer listener for DrawerLayout
        drawerLayout.setDrawerListener(drawerToggle);

        //Sync State of the navigation icon on the toolbar
        // with the drawer when the drawer is opened or closed.
        drawerToggle.syncState();



    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            selectItem(position);
            drawerLayout.closeDrawers();

        }

    }

    private void selectItem(int position) {

        // Toast.makeText(getApplicationContext(), "Item Clicked " + mNavigationDrawerItemTitles[position]  , Toast.LENGTH_SHORT).show();

        switch (position) {
            case 0:
                break;
            case 1:
                Intent home = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(home);
                break;
            case 2:
                Intent booking = new Intent(getApplicationContext(), BookingListActivity.class);
                startActivity(booking);
                break;
            case 3:
                Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profile);
                break;
            default:
                break;
        }
    }



    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
