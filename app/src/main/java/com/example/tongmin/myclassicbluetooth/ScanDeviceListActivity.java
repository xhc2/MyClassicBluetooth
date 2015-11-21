package com.example.tongmin.myclassicbluetooth;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class ScanDeviceListActivity extends AppCompatActivity {

    private ListView listview;
    private AdapterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_device_list);
        listview = (ListView)findViewById(R.id.listview);
        adapter = new AdapterAdapter();
        listview.setAdapter(adapter);

    }

}
