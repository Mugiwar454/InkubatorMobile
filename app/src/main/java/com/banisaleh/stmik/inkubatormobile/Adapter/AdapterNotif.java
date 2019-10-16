package com.banisaleh.stmik.inkubatormobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.banisaleh.stmik.inkubatormobile.Models.GetNotif;
import com.banisaleh.stmik.inkubatormobile.R;

import java.util.List;

public class AdapterNotif extends ArrayAdapter<GetNotif> {
        List<GetNotif> listViewNotif;
        Context context;
        int layout;
        private Activity activity;

        public AdapterNotif(Context context, List<GetNotif> list) {
        super(context, R.layout.listview_notif, list);
        this.context = context;
        this.layout = layout;
        this.listViewNotif = list;
        }
        public int getCount() {
                return listViewNotif.size();
                }

        public long getItemId(int position) {
                return position;
                }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
            convertView = View.inflate(context, R.layout.listview_notif, null);
            }

            TextView txt1 = (TextView) convertView.findViewById(R.id.date_mess);
            TextView txt2 = (TextView) convertView.findViewById(R.id.isi_mess);


            GetNotif history = listViewNotif.get(position);
            txt1.setText(String.valueOf(history.getTgl_mess()));
            txt2.setText(String.valueOf(history.getPesan()));

            return convertView;
        }

}
