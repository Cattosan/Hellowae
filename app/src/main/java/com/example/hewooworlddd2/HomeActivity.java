package com.example.hewooworlddd2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.tabs.TabLayout;

import static android.app.FragmentTransaction.*;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private Switch wifiSwitch;
    private WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiSwitch = findViewById(R.id.wifi_switch);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    wifiSwitch.setText("WiFi is ON");
                } else {
                    wifiManager.setWifiEnabled(false);
                    wifiSwitch.setText("WiFi is OFF");
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiSwitch.setChecked(true);
                    wifiSwitch.setText("WiFi is ON");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiSwitch.setChecked(false);
                    wifiSwitch.setText("WiFi is OFF");
                    break;
            }
        }
    };
}

public class HomeActivity extends AppCompatActivity {
    //    private static final String TAG = HomeActivity.class.getSimpleName();
    private TabItem tabLeft;
    private TabItem tabRight;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        tabLeft = findViewById(R.id.tabItemL);
        tabRight = findViewById(R.id.tabItemR);
        tabLayout = findViewById(R.id.tabLayout);
//        fragmentHolder = findViewById(R.id.fragmentPlaceHolder);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, new FragmentTop());
        fragmentTransaction.commit();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                if(tab.getPosition() == 0){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentPlaceHolder, new FragmentTop());
                    fragmentTransaction.commit();
                    Log.d("success", "Left Tab");
                }else if(tab.getPosition() == 1){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentPlaceHolder, new FragmentBottom());
                    fragmentTransaction.commit();
                    Log.d("success", "Right Tab");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
            Log.d("success", "on Resume: Landscape");
        }else{
            Log.d("success", "onResume: Portrait");
        }
    }

}
