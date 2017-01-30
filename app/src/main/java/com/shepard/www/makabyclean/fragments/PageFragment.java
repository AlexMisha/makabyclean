package com.shepard.www.makabyclean.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shepard.www.makabyclean.R;
import com.shepard.www.makabyclean.databinding.FragmentPageBinding;
import com.shepard.www.makabyclean.models.Page;

public class PageFragment extends Fragment {

  private String url = "http://www.makabyclean.ru";

  private FragmentPageBinding binding;

  public PageFragment() {
    // Empty constructor
  }

  public View onCreateView(LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_page, container, false);
    View view = binding.getRoot();
    binding.setPage(new Page());

    return view;
  }
}
