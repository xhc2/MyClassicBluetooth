package com.example.tongmin.myclassicbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

/**
 * 扫描新设备
 */
public class ScanDeviceListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listview;
    private AdapterAdapter adapter;
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scan_device_list);

        listview = (ListView)findViewById(R.id.listview);
        adapter = new AdapterAdapter();
        listview.setAdapter(adapter);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,R.layout.item);
        mNewDevicesArrayAdapter2 = new ArrayAdapter<String>(this,R.layout.item);
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if(pairedDevices.size() > 0){
            for(BluetoothDevice d : pairedDevices){
                mNewDevicesArrayAdapter.add("name:"+d.getName()+"\n address:"+d.getAddress());
            }
        }
        adapter.addAdpater(mNewDevicesArrayAdapter);
        Button btScan = new Button(this);
        btScan.setText("扫描设备");
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDiscovery();
            }
        });
        //因为这个button是要加到listview中所以LayoutParams要用AbsListView
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        btScan.setLayoutParams(params);
        adapter.addView(btScan);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }
    /**
     * The BroadcastReceiver that listens for discovered devices and changes the title when
     * discovery is finished
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter2.add(device.getName()+"\n "+device.getAddress());
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(ScanDeviceListActivity.this, "完成搜索", Toast.LENGTH_SHORT).show();
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                }
            }
        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {


        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }
}
