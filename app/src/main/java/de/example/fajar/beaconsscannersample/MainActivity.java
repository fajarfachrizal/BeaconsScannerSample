package de.example.fajar.beaconsscannersample;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    protected static final String TAG = "MonitoringAcitivity";
    private BeaconManager beaconManager;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    List<BeaconData> mBeaconData = new ArrayList<>();
    ListView listView;
    BeaconAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);

        Intent enableblt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableblt, 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("this app need location access");
                builder.setMessage("please grant location access so this app can detect beacons. ");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION); }
                }); builder.show();
            }
        }
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        //beacon monitoring
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {

                Log.i(TAG, "I see a beacon");
            }

            @Override
            public void didExitRegion(Region region) {

                Log.i(TAG, "I no longer see a beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
                Log.i(TAG,region.toString());
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId",null,null,null));//Identifier.parse("e2c56db5-dffb-48d2-b060-d0f5a71096e0"), Identifier.parse("2"), Identifier.parse("234")));
        } catch (RemoteException e) {    }

        //beacon ranging


        beaconManager.addRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                if (beacons.size() > 0) {
//                    Log.i(TAG,beacons.toString());
                    //data.clear();
                    mBeaconData.clear();
                    for (Beacon beacon : beacons) {
                        mBeaconData.add(new BeaconData(String.valueOf(beacon.getId1())
                                ,String.valueOf(beacon.getId3())
                                ,String.valueOf(beacon.getId2())
                                ,String.valueOf(beacon.getTxPower())
                                ,String.valueOf(beacon.getDistance())
                                ,String.valueOf(beacon.getRssi()),
                                beacon.getBluetoothAddress()));
                    }

                    updateList();
                }

            }

        });


        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent i) {
        super.onActivityResult(request_code, result_code, i);
        switch (request_code) {
            case 1:
                if (result_code == RESULT_OK) {
                    Log.d(TAG, "bluetooth enabled");
                } else if (result_code == RESULT_CANCELED) {
                    Log.d(TAG, "permission denied");
                    finish();
                }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    public void updateList(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // adapter.add(data);
                adapter = new BeaconAdapter(getApplicationContext(),mBeaconData);
                listView.setAdapter(adapter);
            }
        });
    }


}
