package com.shepard.www.makabyclean.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shepard.www.makabyclean.fragments.PageFragment;
import com.shepard.www.makabyclean.fragments.ServicesFragment;
import com.shepard.www.makabyclean.models.Page;

import java.util.List;

/**
 * Created by Полина on 19.01.2017.
 */

public class PagesAdapter extends FragmentPagerAdapter {

  private List<Page> pages;

  public PagesAdapter(FragmentManager fm, List<Page> pages) {
    super(fm);
    this.pages = pages;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position){
      case 0: return new PageFragment();
      case 1: return new ServicesFragment();
      case 2: return new PageFragment();

      default: return new PageFragment();
    }
  }

  @Override
  public int getCount() {
    return pages.size();
  }
}
