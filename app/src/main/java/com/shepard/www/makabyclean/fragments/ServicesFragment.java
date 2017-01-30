package com.shepard.www.makabyclean.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shepard.www.makabyclean.R;
import com.shepard.www.makabyclean.databinding.FragmentServicesBinding;
import com.shepard.www.makabyclean.models.Page;

public class ServicesFragment extends Fragment {

  private String url = "http://www.makabyclean.ru";

  private FragmentServicesBinding binding;

  public ServicesFragment(){
    //empty constructor
  }

  public View onCreateView(LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_services, container, false);
    View view = binding.getRoot();
    binding.setPage(new Page());
    binding.webView.loadUrl(url);

    return view;
  }
}
