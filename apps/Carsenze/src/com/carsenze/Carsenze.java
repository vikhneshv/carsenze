package com.carsenze;

import android.content.Context;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.os.ServiceManager;
import android.os.IBinder;
import android.hardware.carsenze.ICarsenze;

public class Carsenze extends Activity {
    private static final String TAG = "Carsenze";
    private static final String IINVCASE_AIDL_INTERFACE = "android.hardware.carsenze.ICarsenze/default";
    private static ICarsenze carsenzeAJ; // AIDL Java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fetch = (Button)findViewById(R.id.button);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView mem = (EditText)findViewById(R.id.Memory);
                TextView cpu = (EditText)findViewById(R.id.CPU);
                TextView network = (EditText)findViewById(R.id.Network);
                String ret = "";

                if(carsenzeAJ != null) {
                    try {
                        ret = carsenzeAJ.getMemStats(txt);
                        Log.d(TAG, "App: get= " + ret);
                        mem.setText(ret);
                    } catch (android.os.RemoteException e) {
                        Log.e(TAG, "ICarsenze-AIDL error", e);
                    }
                }

                if(carsenzeAJ != null) {
                    try {
                        ret = carsenzeAJ.getCpuStats(txt);
                        Log.d(TAG, "App: get= " + ret);
                        cpu.setText(ret);
                    } catch (android.os.RemoteException e) {
                        Log.e(TAG, "ICarsenze-AIDL error", e);
                    }
                }

                if(carsenzeAJ != null) {
                    try {
                        ret = carsenzeAJ.getNetworkStats(txt);
                        Log.d(TAG, "App: get= " + ret);
                        network.setText(ret);
                    } catch (android.os.RemoteException e) {
                        Log.e(TAG, "ICarsenze-AIDL error", e);
                    }
                }
            }
        });

        IBinder binder = ServiceManager.getService(ICARSENZE_AIDL_INTERFACE);
        if (binder == null) {
            Log.e(TAG, "Getting " + ICARSENZE_AIDL_INTERFACE + " service daemon binder failed!");
        } else {
            carsenzeAJ = ICarsenze.Stub.asInterface(binder);
            if (carsenzeAJ == null) {
                Log.e(TAG, "Getting ICarsenze AIDL daemon interface failed!");
            } else {
                Log.d(TAG, "ICarsenze AIDL daemon interface is binded!");
            }
        }
    }
}
