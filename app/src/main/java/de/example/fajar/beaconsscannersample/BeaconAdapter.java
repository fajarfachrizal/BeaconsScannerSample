package de.example.fajar.beaconsscannersample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BeaconAdapter extends BaseAdapter{
    Context mContext;
    List<BeaconData> mBeaconData;

    public BeaconAdapter(Context mContext, List<BeaconData> mBeaconData) {
        this.mContext = mContext;
        this.mBeaconData = mBeaconData;
    }

    @Override
    public int getCount() {
        return mBeaconData.size();
    }

    @Override
    public Object getItem(int position) {
        return mBeaconData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.display, null);
        TextView uuid = (TextView) v.findViewById(R.id.uuid);
        TextView major = (TextView) v.findViewById(R.id.major);
        TextView minor = (TextView) v.findViewById(R.id.minor);
        TextView rssi = (TextView) v.findViewById(R.id.rssi);
        TextView tx = (TextView) v.findViewById(R.id.tx);
        TextView distance = (TextView) v.findViewById(R.id.distance);
        TextView address = (TextView) v.findViewById(R.id.address);

        uuid.setText(mBeaconData.get(position).getUuid());
        major.setText(mBeaconData.get(position).getMajor());
        minor.setText(mBeaconData.get(position).getMinor());
        rssi.setText(mBeaconData.get(position).getRssi());
        tx.setText(mBeaconData.get(position).getTx());
        distance.setText(mBeaconData.get(position).getDistance());
        address.setText(mBeaconData.get(position).getAddress());
        return v;
    }
}
