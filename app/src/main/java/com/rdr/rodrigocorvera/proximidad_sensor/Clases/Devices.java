package com.rdr.rodrigocorvera.proximidad_sensor.Clases;

/**
 * Created by Rodrigo Corvera on 9/11/2018.
 */

public class Devices {

    private String deviceName;
    private String deviceAddress;

    public Devices(String deviceName, String deviceAddress){
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }
}
