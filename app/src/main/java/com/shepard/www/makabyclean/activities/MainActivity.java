package com.shepard.www.makabyclean.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shepard.www.makabyclean.R;
import com.shepard.www.makabyclean.adapters.PagesAdapter;
import com.shepard.www.makabyclean.databinding.ActivityMainBinding;
import com.shepard.www.makabyclean.models.Page;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;

  private String Tag = ".MainActivity";

  private List<Page> pages;
  private List<MenuItem> menuItems;

  private ActionBarDrawerToggle toggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    Page page = new Page();
    binding.setPage(page);
    initializeToolbar();

    pages = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      pages.add(new Page());
    }

    menuItems = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      menuItems.add(binding.nvView.getMenu().getItem(i));
    }

    initializeViewPager();
    setupDrawerContent(binding.nvView);
    toggle = setupDrawerToggle();
    binding.drawerLayout.addDrawerListener(toggle);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

    initFab();

  }

  public void initFab() {
    binding.fab.setVisibility(View.VISIBLE);
    binding.fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:88126071747"));
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
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

  public void initializeViewPager() {
    binding.viewPager.setAdapter(new PagesAdapter(getSupportFragmentManager(), pages));
    binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        selectDrawerItem(menuItems.get(position));
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
    binding.viewPager.setCurrentItem(0);
  }

  public void selectDrawerItem(MenuItem menuItem) {
    for (int i = 0; i < 3; i++) {
      if (menuItems.get(i).isChecked()) {
        menuItems.get(i).setChecked(false);
      }
    }
    menuItem.setChecked(true);
    setTitle(menuItem.getTitle());
    binding.drawerLayout.closeDrawers();
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.getMenu().getItem(0).setChecked(true);
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int index = (menuItem.getItemId() % 10 - 2) % 10;
            Log.d(Tag, "index: " + index);
            binding.viewPager.setCurrentItem(index);
            return true;
          }
        });
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
}