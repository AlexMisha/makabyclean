package com.shepard.www.makabyclean.activities;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.shepard.www.makabyclean.R;
import com.shepard.www.makabyclean.SplashScreen;
import com.shepard.www.makabyclean.databinding.ActivityMainBinding;
import com.shepard.www.makabyclean.fragments.PageFragment;
import com.shepard.www.makabyclean.models.Page;
import com.shepard.www.makabyclean.utils.NavigationUtil;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;

  private String Tag = ".MainActivity";

  private GoogleSignInAccount googleAccount;

  private ActionBarDrawerToggle toggle;

  private NavigationUtil navigationUtil;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    navigationUtil = new NavigationUtil(binding, getFragmentManager());
    Page page = new Page();
    binding.setPage(page);
    initializeToolbar();

    setupDrawerContent(binding.nvView);
    toggle = setupDrawerToggle();
    binding.drawerLayout.addDrawerListener(toggle);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

    if (savedInstanceState == null) {
      navigationUtil.setCurrentFragment(0);
    }

    initFab();

  }

  public void initFab() {
    binding.fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:89062502880"));
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
            Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
          return;
        }
        startActivity(intent);
      }
    });
  }

  private ActionBarDrawerToggle setupDrawerToggle() {
    return new ActionBarDrawerToggle(this, binding.drawerLayout, binding.mainActivityToolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
  }

  public void initializeToolbar() {
    setSupportActionBar(binding.mainActivityToolbar);
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.getMenu().getItem(0).setChecked(true);
    navigationView.setNavigationItemSelectedListener(navigationUtil);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.toolbar_menu, menu);
    return true;
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  public GoogleSignInAccount getGoogleAccount() {
    return googleAccount;
  }

  public void setGoogleAccount(GoogleSignInAccount googleAccount) {
    if (googleAccount != null) {
      this.googleAccount = googleAccount;
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (toggle.onOptionsItemSelected(item)) {
      return true;
    }

    switch (item.getItemId()) {
      case android.R.id.home:
        binding.drawerLayout.openDrawer(GravityCompat.START);
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    toggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    toggle.onConfigurationChanged(newConfig);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putBoolean("isScreenOrientationChanged", true);
    super.onSaveInstanceState(outState);
  }
}
