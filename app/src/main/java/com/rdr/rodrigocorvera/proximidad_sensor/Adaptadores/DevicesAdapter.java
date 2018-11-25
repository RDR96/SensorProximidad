package com.rdr.rodrigocorvera.proximidad_sensor.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdr.rodrigocorvera.proximidad_sensor.Clases.Devices;
import com.rdr.rodrigocorvera.proximidad_sensor.MainActivity;
import com.rdr.rodrigocorvera.proximidad_sensor.R;


import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Rodrigo Corvera on 9/11/2018.
 */

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder>{

    Context context;
    List<Devices> devices;

    public DevicesAdapter(Context context, List<Devices> data) {
        this.context = context;
        this.devices = data;
    }


    @Override
    public DevicesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.device_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DevicesAdapter.MyViewHolder holder, final int position) {
        holder.deviceName.setText(devices.get(position).getDeviceName());
        holder.deviceAddress.setText(devices.get(position).getDeviceAddress());

        holder.deviceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, devices.get(position).getDeviceAddress());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView deviceName;
        private TextView deviceAddress;
        private LinearLayout deviceLayout;

        public MyViewHolder (View itemView){
            super(itemView);
            deviceName = itemView.findViewById(R.id.nombre_dispositivo);
            deviceAddress = itemView.findViewById(R.id.direccion_dispositivo);
            deviceLayout = itemView.findViewById(R.id.device_layout);
        }


    }
}
