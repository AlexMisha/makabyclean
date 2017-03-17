package com.shepard.www.makabyclean.fragments;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.shepard.www.makabyclean.R;
import com.shepard.www.makabyclean.activities.MainActivity;
import com.shepard.www.makabyclean.databinding.FragmentOrderBinding;
import com.shepard.www.makabyclean.models.Page;

public class OrderFragment extends Fragment {

  private String url = "http://www.makabyclean.ru";

  private FragmentOrderBinding binding;

  private GoogleSignInAccount googleAccount;

  private MainActivity activity;

  private String name;
  private String email;
  private String phone;
  private Integer orderType;

  private boolean authorized;

  public OrderFragment() {
    //empty constructor
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (savedInstanceState != null) {
      name = savedInstanceState.getString("name");
      email = savedInstanceState.getString("email");
      phone = savedInstanceState.getString("phone");
      orderType = savedInstanceState.getInt("order_type");
      if (savedInstanceState.getBoolean("isAuthorized", false)) {
        authorized = true;
        binding.signInButton.setVisibility(View.INVISIBLE);
      }
      setFields();
    }
  }

  public View onCreateView(LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_order, container, false);
    View view = binding.getRoot();
    binding.setPage(new Page());
    activity = (MainActivity) getActivity();

    initSpinner();
    initSignInButton(getActivity());

    if ((activity.getGoogleAccount() != null) || (googleAccount != null)) {
      googleAccount = activity.getGoogleAccount();
      binding.signInButton.setVisibility(View.INVISIBLE);
      setParams();
      setFields();
    }

    return view;
  }

  private void initSpinner() {
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(binding.getRoot().getContext(),
        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.services));
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    binding.spinner.setAdapter(adapter);
    //binding.spinner.setSelection(1);
  }

  private void initSignInButton(final Activity activity) {
    binding.signInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        googleSignInOptions(activity);
      }
    });
  }

  private void googleSignInOptions(final Activity activity) {
    GoogleSignInOptions gso =
        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().
            requestScopes(new Scope("https://www.googleapis.com/auth/contacts.readonly"))
            .build();

    GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(
        binding.getRoot().getContext())
        .enableAutoManage((FragmentActivity) activity,
            new GoogleApiClient.OnConnectionFailedListener() {
              @Override
              public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(activity, "ConnectionFailed", Toast.LENGTH_SHORT).show();
              }
            })
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent, 3);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 3) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if (result.isSuccess()) {
        GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
        if (googleSignInAccount != null) {
          googleAccount = googleSignInAccount;
          activity.setGoogleAccount(googleAccount);
          setParams();
          setFields();
          binding.signInButton.setVisibility(View.INVISIBLE);
        }
      } else {
        Toast.makeText(getActivity(), "Соединение прервано", Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void setParams() {
    name = googleAccount.getDisplayName();
    email = googleAccount.getEmail();
  }

  private void setFields() {
    if (name != null) {
      binding.firstName.setText(name);
    }
    if (email != null) {
      binding.email.setText(email);
    }
    if (phone != null) {
      binding.phone.setText(phone);
    }
    if (orderType != null) {
      binding.spinner.setSelection(orderType);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("name", binding.firstName.getText().toString());
    outState.putString("email", binding.email.getText().toString());
    outState.putString("phone", binding.phone.getText().toString());
    outState.putInt("order_type", binding.spinner.getSelectedItemPosition());
    if ((googleAccount != null) || (authorized)) {
      outState.putBoolean("isAuthorized", true);
    }
  }
}
