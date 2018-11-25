package com.rdr.rodrigocorvera.proximidad_sensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rdr.rodrigocorvera.proximidad_sensor.Adaptadores.DevicesAdapter;
import com.rdr.rodrigocorvera.proximidad_sensor.Clases.Devices;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Rodrigo Corvera on 8/11/2018.
 */

public class BluetoothHelper extends AppCompatActivity {



    ListView idLista;


    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> dispositivosVinculados;

    RecyclerView recyclerView;
    DevicesAdapter devicesAdapter;

    ArrayList<Devices> dispositivos;

    public static String EXTRA_ADDRESS = "device_address";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        recyclerView = findViewById(R.id.devices_recyclerView);

        dispositivos = new ArrayList<Devices>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(!bluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,1);
        }else{
            listarDispositivos();
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            listarDispositivos();
        }
    }

    private void listarDispositivos(){
        dispositivosVinculados = bluetoothAdapter.getBondedDevices();


        if(dispositivosVinculados.size() > 0){
            for(BluetoothDevice bt : dispositivosVinculados){
                dispositivos.add(new Devices(bt.getName(), bt.getAddress()));
            }
        }else{
            Toast.makeText(getApplicationContext(), "No hay dispositivos", Toast.LENGTH_SHORT).show();
        }

        devicesAdapter = new DevicesAdapter(getApplicationContext(), dispositivos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(devicesAdapter);

    }



}
