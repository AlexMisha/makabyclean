package com.shepard.www.makabyclean.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.shepard.www.makabyclean.R;
import com.shepard.www.makabyclean.databinding.FragmentOrderBinding;
import com.shepard.www.makabyclean.models.Page;

public class OrderFragment extends Fragment {

  private String url = "http://www.makabyclean.ru";

  private FragmentOrderBinding binding;

  public OrderFragment() {
    //empty constructor
  }

  public View onCreateView(LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_order, container, false);
    View view = binding.getRoot();
    binding.setPage(new Page());

    initSpinner();

    return view;
  }

  public void initSpinner() {
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(binding.getRoot().getContext(),
        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.services));
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    binding.spinner.setAdapter(adapter);
    //binding.spinner.setSelection(1);
  }
}
