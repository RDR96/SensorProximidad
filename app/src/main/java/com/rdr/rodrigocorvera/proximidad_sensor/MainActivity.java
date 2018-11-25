package com.rdr.rodrigocorvera.proximidad_sensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorManager;
    Sensor cursorProximidad;
    String address;

    private BluetoothAdapter bluetoothAdapter;

    private BluetoothSocket bluetoothSocket;
    private boolean isBTConnected = false;

    static final UUID identificadorDispositivo = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    TextView contadorElementos;
    int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent getIntent = getIntent();
        address = getIntent.getStringExtra(Intent.EXTRA_TEXT);
        //msg(address);
        msg(identificadorDispositivo.toString());
        contadorElementos = findViewById(R.id.contador_elementos);

        new BTConnet().execute();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        cursorProximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        Log.d("Maximo rango", String.valueOf(cursorProximidad.getMaximumRange()));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float i = event.values[0];

        if(bluetoothSocket != null && isBTConnected){

            try {
                if(i == 0.0){

                    bluetoothSocket.getOutputStream().write("1".toString().getBytes());



                    contador++;
                    contadorElementos.setText(String.valueOf(contador));
                    contadorElementos.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                }
                else if(i == 8.0){
                    bluetoothSocket.getOutputStream().write("0".toString().getBytes());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Log.d("Valor de proximidad", String.valueOf(i));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void msg(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, cursorProximidad, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);

    }

    private class BTConnet extends AsyncTask<Void,Void,Void> {
        private boolean isConnectSuccess = true;

        @Override
        protected Void doInBackground(Void... voids) {

            if(bluetoothSocket != null || !isBTConnected)
            {

                try {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
                    bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(identificadorDispositivo);
                    bluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();

                } catch (IOException e) {
                    e.printStackTrace();
                    isConnectSuccess = false;
                }

            }

            return null;
        }

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected void onPostExecute(Void result){
            if(!isConnectSuccess){
                msg("Conexion fallida");
                finish();
            }else{
                msg("Esta conectado");
                isBTConnected = true;
            }
        }

    }
}
