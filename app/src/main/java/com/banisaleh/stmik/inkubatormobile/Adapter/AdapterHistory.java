package com.banisaleh.stmik.inkubatormobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.banisaleh.stmik.inkubatormobile.Models.GetHistory;
import com.banisaleh.stmik.inkubatormobile.R;

import java.util.List;

public class AdapterHistory extends ArrayAdapter<GetHistory> {
    List<GetHistory> listView;
    Context context;
    int layout;
    private Activity activity;

    public AdapterHistory(Context context, List<GetHistory> list) {
        super(context, R.layout.list_histrory, list);
        this.context = context;
        this.layout = layout;
        this.listView = list;
    }
    public int getCount() {
        return listView.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.list_histrory, null);
        }

        TextView txt1 = (TextView) convertView.findViewById(R.id.tgl);
        TextView txt2 = (TextView) convertView.findViewById(R.id.dname);
        TextView txt3 = (TextView) convertView.findViewById(R.id.suhu);
        TextView txt4 = (TextView) convertView.findViewById(R.id.humidity);
        TextView txt5 = (TextView) convertView.findViewById(R.id.lampu1);
        TextView txt6 = (TextView) convertView.findViewById(R.id.lampu2);

        GetHistory history = listView.get(position);
        txt1.setText(String.valueOf(history.getDate()));
        txt2.setText(String.valueOf(history.getDevice_name()));
        txt3.setText(String.valueOf(history.getTemp()));
        txt4.setText(String.valueOf(history.getHumidity()));
        txt5.setText(String.valueOf(history.getLampu_1()));
        txt6.setText(String.valueOf(history.getLampu_2()));

        return convertView;
    }
}
