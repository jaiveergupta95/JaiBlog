package com.jaiveer.jaiblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private FrameLayout frameLayout;
    NavigationView navigationView;

    String token = "";

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDrawerLayout = findViewById(R.id.nav_drawal_layouts);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout,
                toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        getData();


        /*if(savedInstanceState == null){
            //getSupportFragmentManager().beginTransaction().replace(R.id.frame_layouts,new HomeFragment()).commit();
            //navigationView.setCheckedItem(R.id.nav_home);
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }*/

    }

    //retrofit api code start
    private void getData(){

        /*String url = BloogerApi.BASE_URL + "?key=" + BloogerApi.API_KEY;
        if(token != ""){
            url = url+ "&pageToken="+ token;
        }
        if(token == null){
            return;
        }*/

        Call<PostList> postList = BloogerApi.getService().getPostList();
        postList.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList list = response.body();

                recyclerView.setAdapter(new PostAdapter(MainActivity.this,list.getItems()));
                //Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int selectId = menuItem.getItemId();
        switch (selectId) {
            case R.id.nav_home:
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_android:
                //getSupportFragmentManager().beginTransaction().replace(R.id.frame_layouts,new InboxFragment()).commit();
                Toast.makeText(this, "Android", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_iso:
                //getSupportFragmentManager().beginTransaction().replace(R.id.frame_layouts,new AlertFragment()).commit();
                Toast.makeText(this, "Apple", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sub:
                //getSupportFragmentManager().beginTransaction().replace(R.id.frame_layouts,new FavoriteFragment()).commit();
                Toast.makeText(this, "Subscribe", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_aboutus:
                //getSupportFragmentManager().beginTransaction().replace(R.id.frame_layouts,new SubscribeFragment()).commit();
                Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_setting:
                //getSupportFragmentManager().beginTransaction().replace(R.id.frame_layouts,new ShareFragment()).commit();
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
