package com.banisaleh.stmik.inkubatormobile;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.banisaleh.stmik.inkubatormobile.Helper.AppConstant;
import com.banisaleh.stmik.inkubatormobile.Models.GetDisable;
import com.banisaleh.stmik.inkubatormobile.Models.GetHome;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView dateView1,vSuhu,vHumi,vLampu1,vLampu2;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    String sDate1,sDate2;

    private Button btnEnd;
    Handler mHandler;
    private ProgressDialog progressDialog;

    String device_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        vSuhu = (TextView)findViewById(R.id.tvSuhu);
        vHumi = (TextView)findViewById(R.id.tvHumi);
        vLampu1 = (TextView)findViewById(R.id.lampu1);
        vLampu2 = (TextView)findViewById(R.id.lampu2);

        dateFormatter = new SimpleDateFormat("dd-MMMM-yyyy  HH:mm:ss z", Locale.US);

        dateView1 = (TextView) findViewById(R.id.tgl1);
        showDate1();

        this.mHandler = new Handler();
        m_Runnable.run();

        sharedPreferences = getSharedPreferences(AppConstant.myPreferenced, MODE_PRIVATE);

        btnEnd = (Button)findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new endDevice().execute();

            }
        });
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run()

        {
            new DataDashboard().execute();
            //Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT).show();
            MainActivity.this.mHandler.postDelayed(m_Runnable,1000);
        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        new DataDashboard().execute();
    }

    private void showDate1() {
        Date curDate = new Date();
        dateFormatter = new SimpleDateFormat("dd MMMM yyyy      HH:mm z");
        sDate1 = dateFormatter.format(curDate);
        dateView1.setText(sDate1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Menerapkan menu terpilih di menu-> empat.xml
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_history) {
            Intent histori = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(histori);
        }
        else if(id == R.id.action_notifikasi) {
            Intent notif = new Intent(MainActivity.this, NotifActivity.class);
            startActivity(notif);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public class DataDashboard extends AsyncTask<String, Void, GetHome> {
        @Override
        protected GetHome doInBackground(String... strings) {
            try {
                return AppConstant.getServiceApi().getHome(MainActivity.this);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(GetHome getDataDashboard) {
            super.onPostExecute(getDataDashboard);
            if (getDataDashboard != null) {
                vSuhu.setText(getDataDashboard.getTemp());
                vHumi.setText(getDataDashboard.getHumidity());
                vLampu1.setText(getDataDashboard.getLampu_1());
                vLampu2.setText(getDataDashboard.getLampu_2());

            } else {
                //Toast.makeText(HomeActivity.this, "Telah Terjadi Kesalahan Koneksi", Toast.LENGTH_SHORT).show();
                vSuhu.setText("Koneksi Terputus");
                vHumi.setText("Koneksi Terputus");
                vLampu1.setText("Koneksi Terputus");
                vLampu2.setText("Koneksi Terputus");
            }
        }
    }

    public class endDevice extends AsyncTask<String, Void, GetDisable> {

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        @SuppressLint("WrongThread")
        @Override
        protected GetDisable doInBackground(String... strings) {
            device_name = sharedPreferences.getString("device_name","");
            try {
                return AppConstant.getServiceApi().getDisable(MainActivity.this,device_name);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("ApplySharedPref")
        @Override
        protected void onPostExecute(GetDisable getD) {
            super.onPostExecute(getD);
            dialog.dismiss();
            if (getD != null) {
                if (getD.getStatus().equalsIgnoreCase("success")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id_devices", getD.getId_devices());
                    editor.putString("device_name", getD.getDevice_name());
                    editor.putString("tgl_mulai", getD.getTgl_mulai());
                    editor.putString("tgl_selesai", getD.getTgl_selesai());
                    editor.putString("device_status",getD.getDevice_status());
                    editor.putString("total", getD.getTotal());
                    editor.putString("mess", getD.getMess());
                    editor.putString("status", getD.getStatus());

                    Intent in = new Intent(MainActivity.this, HasilActivity.class);
                    in.putExtra("id_devices", getD.getId_devices());
                    in.putExtra("device_name", getD.getDevice_name());
                    in.putExtra("tgl_mulai", getD.getTgl_mulai());
                    in.putExtra("tgl_selesai", getD.getTgl_selesai());
                    in.putExtra("total", getD.getTotal());
                    in.putExtra("device_status",getD.getDevice_status());
                    in.putExtra("status",getD.getStatus());
                    in.putExtra("mess",getD.getMess());
                    startActivity(in);

                    editor.commit();
                    editor.apply();

                    Toast.makeText(MainActivity.this, "" + getD.getMess(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "" + getD.getMess(), Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(MainActivity.this, "Please Check Koneksi", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
