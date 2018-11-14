package de.example.fajar.beaconsscannersample;

public class BeaconData {
    private String uuid;
    private String minor;
    private String major;
    private String tx;
    private String distance;
    private String rssi;
    private String address;

    public BeaconData(String uuid, String minor, String major, String tx, String distance, String rssi, String address) {
        this.uuid = uuid;
        this.minor = minor;
        this.major = major;
        this.tx = tx;
        this.distance = distance;
        this.rssi = rssi;
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
