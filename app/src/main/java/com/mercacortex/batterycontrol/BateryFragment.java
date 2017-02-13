package com.mercacortex.batterycontrol;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class BateryFragment extends Fragment {

    private ProgressBar pblevel;
    private TextView txvLevel;
    protected ImageView imgStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, null);
        pblevel = (ProgressBar) rootView.findViewById(R.id.pbLevel);
        txvLevel = (TextView) rootView.findViewById(R.id.txvLevel);
        imgStatus = (ImageView) rootView.findViewById(R.id.imgStatus);

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

        //Crea un IntentFilter para la acción Intent.ACTION_BATTERY_CHANGED
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        //Registra el BroadcastReceiver
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }
    @Override
    public void onPause() {
        super.onPause();
        //Que no reciba el Receiver que tiene asignado. No es lo normal.
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    /**
     * Broadcast que depende del ciclo de vida del Fragment
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Lee la información que llega del Intent: level, status

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            pblevel.setProgress(level);
            txvLevel.setText(level + "%");

            // Estado de la batería
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    imgStatus.setImageResource(R.mipmap.ic_plugged_full);
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    imgStatus.setImageResource(R.mipmap.ic_plugged_charging);
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    imgStatus.setImageResource(R.mipmap.ic_unplugged_discharguing);
                    break;
            }
        }
    };


}
