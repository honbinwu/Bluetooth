package com.example.bluetooth_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1 ;

    TextView BlueTv,PairedTv;
    ImageView BlueIv;
    Button StartBtn,OffBtn,DiscoverBtn,PairedBtn;

    BluetoothAdapter BlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BlueTv = findViewById(R.id.BluetoothTv);
        PairedTv = findViewById(R.id.pairedTv);
        BlueIv= findViewById(R.id.bluetoothIv);
        StartBtn= findViewById(R.id.startbutton);
        OffBtn=findViewById(R.id.offbutton);
        DiscoverBtn=findViewById(R.id.discoverbutton);
        PairedBtn=findViewById(R.id.pairedbutton);

        BlueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (BlueAdapter == null)
        {
            BlueTv.setText("No link");
        }
        else
        {
            BlueTv.setText("LinkStart");
        }

        if (BlueAdapter.isEnabled())
        {
            BlueIv.setImageResource(R.drawable.ic_action_on);
        }
        else
        {
            BlueIv.setImageResource(R.drawable.ic_action_off);

        }

        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BlueAdapter.isEnabled())
                {
                    showToast("Start....");
                    Intent intent= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,REQUEST_ENABLE_BT);
                }
                else
                {
                    showToast("already on");
                }
            }
        });

        DiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!BlueAdapter.isDiscovering())
                {
                    showToast("Your Device Discover");
                    Intent intent= new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });

        OffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BlueAdapter.isEnabled())
                {
                    BlueAdapter.disable();
                    showToast("Off");
                    BlueIv.setImageResource(R.drawable.ic_action_off);

                }
                else
                {
                    showToast("already off");
                }

            }
        });

        PairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BlueAdapter.isEnabled())
                {
                    PairedTv.setText("Paired Device");
                    Set<BluetoothDevice> devices = BlueAdapter.getBondedDevices();
                    for (BluetoothDevice device: devices)
                    {
                        PairedTv.append("\nDevice: " + device.getName()+ "," + device);
                    }
                }
                else
                {
                    showToast("You don't turn on Bluetooth");
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode)
        {
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK)
                {
                    BlueIv.setImageResource(R.drawable.ic_action_on);
                    showToast("BlueTooth is Start");
                }
                else
                {
                    showToast("not on BlueTooth");
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

}
