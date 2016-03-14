package xyz.carlesllobet.pushmarket.UI;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.R;

public class ProductsHolder extends FragmentActivity {

    private Toolbar toolbar;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Uri foto;
    private ImageButton picButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_ranking);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        //setActionBar(toolbar);

        setTitle("Ranking");

        navigationView = (NavigationView) findViewById(R.id.navigationDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView.getMenu().getItem(2).setChecked(true);

        UserFunctions userFunctions = new UserFunctions();
        foto = userFunctions.getFoto(getApplicationContext(), userFunctions.getUserName(getApplicationContext()));
        picButton = (ImageButton) navigationView.getHeaderView(0).findViewById(R.id.picButton);
        if(foto != null) picButton.setImageURI(foto);
        picButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity((new Intent(getApplicationContext(), SettingsActivity.class)));
            }
        });

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.inicio:
                        startActivity((new Intent(getApplicationContext(), HomeActivity.class)));
                        break;
                    case R.id.memory:
                        startActivity((new Intent(getApplicationContext(), MemoryActivity.class)));
                        break;

                    case R.id.ranking:
                        startActivity((new Intent(getApplicationContext(), RankingHolder.class)));
                        break;

                    case R.id.calculator:
                        startActivity((new Intent(getApplicationContext(), CalculatorActivity.class)));
                        break;
                    case R.id.mediaPlayer:
                        startActivity((new Intent(getApplicationContext(), MediaPlayerActivity.class)));
                        break;
                    case R.id.settings:
                        startActivity((new Intent(getApplicationContext(), SettingsActivity.class)));
                        break;
                    case R.id.closeSession:
                        UserFunctions userFunctions = new UserFunctions();
                        userFunctions.logoutUser(getApplicationContext());
                        Intent intent = new Intent(RankingHolder.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
