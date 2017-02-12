package com.shepard.www.makabyclean;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.shepard.www.makabyclean.activities.MainActivity;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by micha on 11/02/2017.
 */

public class SplashScreen extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = new Intent(this, MainActivity.class);

    startActivity(intent);
    finish();
  }
}
