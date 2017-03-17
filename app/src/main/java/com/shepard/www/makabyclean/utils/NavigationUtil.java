package com.shepard.www.makabyclean.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.shepard.www.makabyclean.R;
import com.shepard.www.makabyclean.databinding.ActivityMainBinding;
import com.shepard.www.makabyclean.fragments.OrderFragment;
import com.shepard.www.makabyclean.fragments.PageFragment;

import java.util.ArrayList;
import java.util.List;

public class NavigationUtil implements NavigationView.OnNavigationItemSelectedListener {

  private NavigationView navigationView;

  private ActivityMainBinding binding;

  private FragmentManager fm;

  private List<MenuItem> menuItems;

  private Integer currentFragmentPosition;

  public NavigationUtil(ActivityMainBinding binding,
      FragmentManager fm) {
    this.navigationView = binding.nvView;
    this.binding = binding;
    this.fm = fm;

    menuItems = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      menuItems.add(navigationView.getMenu().getItem(i));
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    if (item.getTitle() == binding.getRoot().getResources().getString(R.string.menu_item_main)) {
      changeFragment(new PageFragment());
      currentFragmentPosition = 0;
      selectItem(item);
    }

    if (item.getTitle() == binding.getRoot()
        .getResources()
        .getString(R.string.menu_item_order)) {
      changeFragment(new OrderFragment());
      currentFragmentPosition = 1;
      selectItem(item);
    }

    binding.drawerLayout.closeDrawers();

    return false;
  }

  private void selectItem(MenuItem item) {
    for (int i = 0; i < 2; i++) {
      if (menuItems.get(i).isChecked()) {
        menuItems.get(i).setChecked(false);
      }
    }
    item.setChecked(true);
    binding.mainActivityToolbar.setTitle(item.getTitle());
  }

  private void changeFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction = fm.beginTransaction();
    fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
  }

  public Integer getCurrentFragmentPosition() {
    return currentFragmentPosition;
  }

  public void setCurrentFragment(Integer currentFragmentPosition) {
    onNavigationItemSelected(menuItems.get(currentFragmentPosition));
  }
}
