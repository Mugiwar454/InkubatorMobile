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

import com.banisaleh.stmik.inkubatormobile.Adapter.AdapterHistory;
import com.banisaleh.stmik.inkubatormobile.Helper.AppConstant;
import com.banisaleh.stmik.inkubatormobile.Models.GetHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ListView listViewH;
    private List<GetHistory> getHistories;
    private SwipeRefreshLayout swip;
    private SharedPreferences sharedPreferences;
    private AdapterHistory adapterHistory;
    String device_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        swip = (SwipeRefreshLayout) findViewById(R.id.swip);

        sharedPreferences = getSharedPreferences(AppConstant.myPreferenced,MODE_PRIVATE);
        listViewH = (ListView)findViewById(R.id.lvHistory);
        listViewH.setTextFilterEnabled(true);
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
        getHistories = new ArrayList<GetHistory>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new getHistory().execute();
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Please Wait", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new getHistory().execute();
                getHistories.clear();
                adapterHistory.notifyDataSetChanged();
                swip.setRefreshing(false);
            }
        }, 5000);
    }

    public class getHistory extends AsyncTask<String, Void, List<GetHistory>> {
        @SuppressLint("WrongThread")
        @Override
        protected List<GetHistory> doInBackground(String... strings) {
            device_name = sharedPreferences.getString("device_name","");
            try {
                return AppConstant.getServiceApi().getHistori(HistoryActivity.this,device_name);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<GetHistory> getHistories) {
            super.onPostExecute(getHistories);
            getHistories = getHistories;
            if (getHistories != null) {
                adapterHistory = new AdapterHistory(HistoryActivity.this, getHistories);
                listViewH.setAdapter(adapterHistory);
            } else {
                Toast.makeText(HistoryActivity.this, "Belum Ada Data", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

