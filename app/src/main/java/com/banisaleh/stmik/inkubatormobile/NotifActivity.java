package com.banisaleh.stmik.inkubatormobile;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.banisaleh.stmik.inkubatormobile.Adapter.AdapterNotif;
import com.banisaleh.stmik.inkubatormobile.Helper.AppConstant;
import com.banisaleh.stmik.inkubatormobile.Models.GetNotif;

import java.util.ArrayList;
import java.util.List;

public class NotifActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ListView listView;
    private List<GetNotif> getNotifs;
    private SwipeRefreshLayout swip;
    private SharedPreferences sharedPreferences;
    private AdapterNotif adapterNotif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        sharedPreferences = getSharedPreferences(AppConstant.myPreferenced, MODE_PRIVATE);
        swip = (SwipeRefreshLayout) findViewById(R.id.swipNotif);
        listView = (ListView)findViewById(R.id.lvNotif);

        listView.setTextFilterEnabled(true);
        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Handler untuk menjalankan jeda selama 5 detik
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // Berhenti berputar/refreshing
                        swip.setRefreshing(false);
                    }
                }, 2000);
            }
        });



        arrayLi();
    }

    private void arrayLi() {
        getNotifs = new ArrayList<GetNotif>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Notif().execute();
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Please Wait", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //new Notif().execute();
                getNotifs.clear();
                adapterNotif.notifyDataSetChanged();
                swip.setRefreshing(false);
            }
        }, 5000);
    }

    public class Notif extends AsyncTask<String, Void, List<GetNotif>> {
        @SuppressLint("WrongThread")
        @Override
        protected List<GetNotif> doInBackground(String... strings) {

            try {
                return AppConstant.getServiceApi().getNotif(NotifActivity.this);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<GetNotif> getNotifs) {
            super.onPostExecute(getNotifs);
            getNotifs = getNotifs;
            if (getNotifs != null) {
                adapterNotif = new AdapterNotif(NotifActivity.this, getNotifs);
                listView.setAdapter(adapterNotif);
            } else {
                //Toast.makeText(HistoryActivity.this, "Belum Ada Data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
